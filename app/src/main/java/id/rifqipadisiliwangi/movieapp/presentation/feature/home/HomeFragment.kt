package id.rifqipadisiliwangi.movieapp.presentation.feature.home

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isInvisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.domain.model.corousel.CorouselDataItem
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResult
import id.rifqipadisiliwangi.core.domain.model.popular.PopularResult
import id.rifqipadisiliwangi.core.domain.state.onError
import id.rifqipadisiliwangi.core.domain.state.onLoading
import id.rifqipadisiliwangi.core.domain.state.onSuccess
import id.rifqipadisiliwangi.core.utils.launchAndCollectIn
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentHomeBinding
import id.rifqipadisiliwangi.movieapp.databinding.HeaderNavigationDrawerBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.corousel.CorouselAdapter
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.load.LoadStateAdapterNowPlaying
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.nowplaying.AdapterNowPlaying
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.popular.AdapterPagingItem
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.home.HomeViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant.COROUSEL_ONE
import id.rifqipadisiliwangi.movieapp.utils.Constant.COROUSEL_THREE
import id.rifqipadisiliwangi.movieapp.utils.Constant.COROUSEL_TWO
import id.rifqipadisiliwangi.movieapp.utils.Constant.MOVIE_POPULAR
import id.rifqipadisiliwangi.movieapp.utils.Constant.NOW_PLAYING
import id.rifqipadisiliwangi.movieapp.utils.Constant.lang_request
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModel()
    private lateinit var handler : Handler
    private lateinit var navHeaderBinding: HeaderNavigationDrawerBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var vpOnboarding: ViewPager2
    private lateinit var adapter: CorouselAdapter
    private lateinit var imageList: ArrayList<CorouselDataItem>

    private var pagingData: PagingData<PopularResult>? = null

    private val adapterPagingPopular by lazy { AdapterPagingItem{navigateToDetail(it) } }
    private val adapterNowPlaying by lazy { AdapterNowPlaying{ navigateToDetailNowPlaying(it) }}
    override fun initView() {
        viewPagerCorousel()
        setUpTransformer()
        listObserveData()
        navHeaderBinding = HeaderNavigationDrawerBinding.inflate(LayoutInflater.from(binding.navigationView.context))
        toggle = ActionBarDrawerToggle(requireActivity(), binding.homeDrawerLayout, R.string.open, R.string.close)
        binding.homeDrawerLayout.addDrawerListener(toggle)
        adapterNowPlaying.addLoadStateListener { loadState -> with(binding){ rvNowPlaying.isInvisible = loadState.refresh is LoadState.Loading } }
        adapterNowPlaying.withLoadStateFooter( footer = LoadStateAdapterNowPlaying { adapterNowPlaying.retry() } )
        toggle.syncState()
        setUpDrawer()
        with(binding){
            rvPopular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvPopular.adapter = adapterPagingPopular
            rvPopular.postDelayed({ rvPopular.smoothScrollToPosition(0) }, 1000)
            rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvNowPlaying.adapter = adapterNowPlaying
            rvNowPlaying.postDelayed({ rvPopular.smoothScrollToPosition(0) }, 1000)
            tvPopular.text = resources.getString(R.string.string_popular)
            tvNowPlaying.text = resources.getString(R.string.string_now_playing)
            btnExplore.text = resources.getString(R.string.string_explore_more)
            tvContent.text = resources.getString(R.string.string_dummy_content_description)
            navHeaderBinding.tvTitleBalance.text = resources.getString(R.string.strting_your_balance)
        }
    }

    private fun setUpDrawer(){
        with(binding){
            navigationView.addHeaderView(navHeaderBinding.root)
            navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.wishlist_item -> activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_wishlistFragment)
                    R.id.history_item -> activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_historyFragment)
                    R.id.cart_item -> activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_cartFragment)
                    R.id.notification_item -> activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_notificationFragment)
                    else -> {}
                }
                homeDrawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            menuToolbar.setOnMenuItemClickListener{ menu ->
                when(menu.itemId){
                    R.id.search_item -> findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                }
                true
            }
        }
    }

    override fun initListener() {
        with(binding){
            menuToolbar.setNavigationOnClickListener {
                homeDrawerLayout.open()
            }
            navHeaderBinding.llTopUp.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_topUpFragment)
                homeDrawerLayout.closeDrawer(GravityCompat.START)
            }
            ivUser.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
            }
            btnExplore.setOnClickListener { intentEksplisit() }
        }
    }
    @SuppressLint("ResourceAsColor")
    override fun observeData() {
        with(viewModel){
            fetchLocalMoviePopular()
            getUrl()
            getTokenBalance()
            isTokenBalance.observe(viewLifecycleOwner){ token ->
                when(token){
                    "0" -> {
                        val color = ContextCompat.getColor(requireContext(), R.color.colorRed)
                        navHeaderBinding.tvContentBalance.setTextColor(color)
                    }
                    else -> {
                        val color = ContextCompat.getColor(requireContext(), R.color.white)
                        navHeaderBinding.tvContentBalance.setTextColor(color)
                    }
                }
                navHeaderBinding.tvContentBalance.text = token
            }
            isUrl.observe(viewLifecycleOwner){ url ->
                binding.ivUser.load(url)
            }
        }
    }

    private fun listObserveData(){
        with(viewModel){
            fetchLocalMoviePopular().launchAndCollectIn(viewLifecycleOwner){state->
                this.launch {
                    delay(1000)
                    state.onLoading {}
                        .onSuccess { data ->
                            pagingData = data
                            adapterPagingPopular.submitData(viewLifecycleOwner.lifecycle, data)
                        }.onError { }
                }
            }
            if (view != null){
                getListNowPlaying(adapterNowPlaying, lang_request)
                parentFragment?.viewLifecycleOwner?.let {
                    viewModel.nowPlayingList.observe(it) { pagingData ->
                        adapterNowPlaying.submitData(lifecycle, pagingData)
                    }
                }
            }
        }
    }

    private fun viewPagerCorousel() {
        vpOnboarding = binding.vpHome
        handler = Handler(Looper.myLooper()!!)

        imageList = arrayListOf(
            CorouselDataItem(COROUSEL_ONE, resources.getString(R.string.string_title_corousel_one), resources.getString(R.string.string_description_one)),
            CorouselDataItem(COROUSEL_TWO, resources.getString(R.string.string_title_corousel_two), resources.getString(R.string.string_description_two)),
            CorouselDataItem(COROUSEL_THREE, resources.getString(R.string.string_title_corousel_three), resources.getString(R.string.string_description_three)),
        )

        adapter = CorouselAdapter(imageList, vpOnboarding)
        vpOnboarding.adapter = adapter
        vpOnboarding.offscreenPageLimit = 3
        vpOnboarding.clipToPadding = false
        vpOnboarding.clipChildren = false
        vpOnboarding.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
                if (position == 3) {
                    vpOnboarding.setCurrentItem(0, true)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable , 2000)
    }
    private val runnable = Runnable {
        vpOnboarding.currentItem = vpOnboarding.currentItem + 1
        vpOnboarding.currentItem = vpOnboarding.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        vpOnboarding.setPageTransformer(transformer)
        vpOnboarding.setPageTransformer(transformer)
    }

    private fun intentEksplisit(){
        val url = "https://www.example.com"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(fallbackIntent)
        }
    }

    private fun navigateToDetail(moviePopular : PopularResult){
        val bundle = Bundle()
        bundle.putParcelable(MOVIE_POPULAR, moviePopular)
        activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    private fun navigateToDetailNowPlaying(nowPlayingPopular : NowPlayingResult){
        val bundle = Bundle()
        bundle.putParcelable(NOW_PLAYING, nowPlayingPopular)
        activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

}