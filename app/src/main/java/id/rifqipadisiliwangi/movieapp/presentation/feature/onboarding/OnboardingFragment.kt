package id.rifqipadisiliwangi.movieapp.presentation.feature.onboarding

import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.domain.model.onboarding.OnboardingDataItem
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentOnboardingBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.onboarding.OnboardingAdapter
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin.PreloginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, PreloginViewModel>(FragmentOnboardingBinding::inflate) {


    private lateinit var vpOnboarding: ViewPager2
    private lateinit var adapter: OnboardingAdapter
    private lateinit var imageList: ArrayList<OnboardingDataItem>
    private lateinit var tabs: TabLayout

    override val viewModel: PreloginViewModel by viewModel()

    override fun initView() {
        setUpvp()
        binding.ivBackgroundOnboarding.load("https://img.freepik.com/free-vector/dark-gradient-background-with-copy-space_53876-99548.jpg?w=900&t=st=1709105083~exp=1709105683~hmac=82a5b42ce1b9c91f23754e83776de8c46466f0ac8570348325b7f64ff6a02b34")
    }

    override fun initListener() {
        with(binding){
            tvNext.setOnClickListener {
                val nextIndex = vpOnboarding.currentItem + 1
                vpOnboarding.setCurrentItem(nextIndex, true)
            }
            tvSkipOnboarding.setOnClickListener {
                findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
            }
            btnAuthentication.setOnClickListener {
                findNavController().navigate(R.id.action_onboardingFragment_to_registerFragment)
            }
            btnAuthentication.text = getString(R.string.string_join_now)
            tvSkipOnboarding.text = getString(R.string.string_skip)
            tvNext.text = getString(R.string.string_next)
        }
    }

    override fun observeData() {}

    private fun setUpvp() {
        vpOnboarding = binding.vpOnboarding
        tabs = binding.tabs

        imageList = arrayListOf(
            OnboardingDataItem("https://getwallpapers.com/wallpaper/full/a/b/e/75026.jpg","Get Started with Vidio: Your Gateway to Endless Entertainment!","Join the Vidio community and unlock a universe of entertainment possibilities! Discover a diverse range of content, from trending series to blockbuster movies, all at your fingertips. With seamless streaming and personalized recommendations, Vidio ensures that every moment is filled with excitement. Start your journey now and immerse yourself in the ultimate viewing experience!"),
            OnboardingDataItem("https://getwallpapers.com/wallpaper/full/1/8/7/109525.jpg","Your Personalized Entertainment Destination Awaits!","Welcome to Vidio, where entertainment meets personalization! Experience the thrill of discovering new favorites tailored just for you. Whether it's the latest dramas, live sports, or original series, Vidio has something for everyone. With user-friendly features and a vast library of content, your entertainment journey begins here. Let Vidio be your guide to a world of endless excitement!"),
            OnboardingDataItem("https://getwallpapers.com/wallpaper/full/c/a/9/75047.jpg","Step into the World of Vidio: Where Every Moment Sparks Joy!","Enter a realm of boundless entertainment with Vidio! Immerse yourself in captivating shows, blockbuster movies, and exclusive content that will keep you entertained for hours on end. With intuitive features and seamless navigation, Vidio ensures that every experience is delightful and unforgettable. Start exploring today and let Vidio brighten your days with excitement and joy!"),
        )

        adapter = OnboardingAdapter(imageList)
        vpOnboarding.adapter = adapter

        TabLayoutMediator(
            tabs,
            vpOnboarding
        ) { tab, _ ->
            tab.customView = layoutInflater.inflate(R.layout.custom_tab_layout, null)
        }.attach()

        vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    2 -> binding.tvNext.isGone = true
                    else -> binding.tvNext.isGone = false
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpOnboarding.unregisterOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {})
    }
}
