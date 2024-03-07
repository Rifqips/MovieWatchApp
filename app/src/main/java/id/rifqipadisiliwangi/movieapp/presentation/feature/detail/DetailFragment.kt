package id.rifqipadisiliwangi.movieapp.presentation.feature.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.domain.model.cart.KeyCart
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResult
import id.rifqipadisiliwangi.core.domain.model.popular.PopularResult
import id.rifqipadisiliwangi.core.domain.model.search.SearchItemResult
import id.rifqipadisiliwangi.core.domain.model.wishlist.KeyWishlist
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentDetailBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart.CartViewModel
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.wishlist.WishListViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant
import id.rifqipadisiliwangi.movieapp.utils.Constant.CART_DETAIL
import id.rifqipadisiliwangi.movieapp.utils.Constant.MOVIE_POPULAR
import id.rifqipadisiliwangi.movieapp.utils.Constant.NOW_PLAYING
import id.rifqipadisiliwangi.movieapp.utils.Constant.SEARCH
import id.rifqipadisiliwangi.movieapp.utils.Constant.WISHLIST_DETAIL
import id.rifqipadisiliwangi.movieapp.utils.Constant.description_result
import id.rifqipadisiliwangi.movieapp.utils.Constant.id_result
import id.rifqipadisiliwangi.movieapp.utils.Constant.img_result
import id.rifqipadisiliwangi.movieapp.utils.Constant.original_result
import id.rifqipadisiliwangi.movieapp.utils.Constant.original_title_result
import id.rifqipadisiliwangi.movieapp.utils.Constant.poster_result
import id.rifqipadisiliwangi.movieapp.utils.Constant.release_date_result
import id.rifqipadisiliwangi.movieapp.utils.Constant.token_result
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, CartViewModel>(FragmentDetailBinding::inflate){

    override val viewModel: CartViewModel by viewModel()
    private val viewModelWishlist : WishListViewModel by viewModel()
    private var sImg = ""
    private var id = ""
    private var originalLanguage = ""
    private var posterPath = ""
    override fun initView() {
        getBundle()
        getBundleCart()
        getBundleWishlist()
        getBundleNowPlaying()
        getBundleSearch()
        updateToken()
        with(binding){
            btnBuy.text = resources.getString(R.string.string_buy_now)
            tvDetailMovie.text = resources.getString(R.string.string_detail_movie)
            tvTitleOverview.text = resources.getString(R.string.string_synopsis)
        }
    }

    override fun initListener() {
        with(binding){
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnBuy.setOnClickListener {
                viewModel.addTransaction(
                    id.toInt(),
                    posterPath,
                    originalLanguage,
                    binding.tvOriginalTitle.text.toString().trim(),
                    binding.tvDescriptionOverview.text.toString().trim(),
                    binding.tvPopularity.text.toString().trim(),
                    posterPath,
                    binding.tvReleaseDate.text.toString().trim(),
                    false
                )
                findNavController().navigateUp()
            }
        }
    }

    override fun observeData() {}

    private fun getBundle(){
        with(binding){
            arguments?.let {
                it.getParcelable<PopularResult>(MOVIE_POPULAR)?.let { detail ->
                    id = detail.id.toString()
                    originalLanguage = detail.originalLanguage
                    posterPath = detail.posterPath
                    ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + detail.backdropPath)
                    sImg = Constant.TMDb_BACKDROP_PATH + detail.backdropPath
                    tvOriginalTitle.text = detail.originalTitle
                    tvPopularity.text = detail.voteCount.toString()
                    tvReleaseDate.text = detail.releaseDate
                    tvDescriptionOverview.text = detail.overview
                    getFavorite(detail.id.toString())
                    ivCart.setOnClickListener {
                        addCart(detail.id, detail.backdropPath, detail.originalLanguage, detail.originalTitle, detail.overview, binding.tvPopularity.text.toString(), detail.posterPath, detail.releaseDate, false)
                    }
                    ivWishlist.setOnClickListener {
                        addWishlist(detail.id, detail.backdropPath, detail.originalLanguage, detail.originalTitle, detail.overview, binding.tvPopularity.text.toString().toInt().toDouble(), detail.posterPath, detail.releaseDate, false)
                    }
                }
            }
        }
    }

    private fun getBundleCart(){
        with(binding){
            arguments?.let {
                it.getParcelable<KeyCart>(CART_DETAIL)?.let { detail ->
                    id = detail.id.toString()
                    originalLanguage = detail.originalLanguage
                    posterPath = detail.posterPath
                    ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + detail.backdropPath)
                    sImg = Constant.TMDb_BACKDROP_PATH + detail.backdropPath
                    tvOriginalTitle.text = detail.originalTitle
                    tvPopularity.text = detail.popularity.toString()
                    tvReleaseDate.text = detail.releaseDate
                    tvDescriptionOverview.text = detail.overview
                    getFavorite(detail.id.toString())
                    ivCart.setOnClickListener {
                        addCart(detail.id, detail.backdropPath, detail.originalLanguage, detail.originalTitle, detail.overview, binding.tvPopularity.text.toString(), detail.posterPath, detail.releaseDate, false)
                    }
                    ivWishlist.setOnClickListener {
                        addWishlist(detail.id, detail.backdropPath, detail.originalLanguage, detail.originalTitle, detail.overview, binding.tvPopularity.text.toString().toInt().toDouble(), detail.posterPath, detail.releaseDate, false)
                    }
                }
            }
        }
    }

    private fun getBundleWishlist(){
        with(binding){
            arguments?.let {
                it.getParcelable<KeyWishlist>(WISHLIST_DETAIL)?.let { detail ->
                    id = detail.id.toString()
                    originalLanguage = detail.originalLanguage
                    posterPath = detail.posterPath
                    ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + detail.backdropPath)
                    sImg = Constant.TMDb_BACKDROP_PATH + detail.backdropPath
                    tvOriginalTitle.text = detail.originalTitle
                    tvPopularity.text = detail.popularity.toString()
                    tvReleaseDate.text = detail.releaseDate
                    tvDescriptionOverview.text = detail.overview
                    getFavorite(detail.id.toString())
                    ivCart.setOnClickListener {
                        addCart(detail.id, detail.backdropPath, detail.originalLanguage, detail.originalTitle, detail.overview, binding.tvPopularity.text.toString(), detail.posterPath, detail.releaseDate, false)
                    }
                    ivWishlist.setOnClickListener {
                        addWishlist(detail.id, detail.backdropPath, detail.originalLanguage, detail.originalTitle, detail.overview, binding.tvPopularity.text.toString().toInt().toDouble(), detail.posterPath, detail.releaseDate, false)
                    }
                }
            }
        }
    }
    private fun getBundleNowPlaying(){
        with(binding){
            arguments?.let {
                it.getParcelable<NowPlayingResult>(NOW_PLAYING)?.let { detail ->
                    id = detail.id.toString()
                    originalLanguage = detail.originalLanguage
                    posterPath = detail.posterPath
                    ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + detail.backdropPath)
                    sImg = Constant.TMDb_BACKDROP_PATH + detail.backdropPath
                    tvOriginalTitle.text = detail.originalTitle
                    tvPopularity.text = detail.voteCount.toString()
                    tvReleaseDate.text = detail.releaseDate
                    tvDescriptionOverview.text = detail.overview
                    getFavorite(detail.id.toString())
                    ivCart.setOnClickListener {
                        addCart(detail.id, detail.backdropPath, detail.originalLanguage, detail.originalTitle, detail.overview, detail.voteCount.toString(), detail.posterPath, detail.releaseDate, false)
                    }
                    ivWishlist.setOnClickListener {
                        addWishlist(detail.id, detail.backdropPath, detail.originalLanguage, detail.originalTitle, detail.overview, detail.voteCount.toString().toInt().toDouble(), detail.posterPath, detail.releaseDate, false)
                    }
                }
            }
        }
    }
    private fun getBundleSearch(){
        with(binding){
            arguments?.let {
                it.getParcelable<SearchItemResult>(SEARCH)?.let { detail ->
                    id = detail.id.toString()
                    originalLanguage = detail.originalLanguage
                    posterPath = detail.posterPath.toString()
                    ivDetailMovie.load(Constant.TMDb_BACKDROP_PATH + detail.backdropPath)
                    sImg = Constant.TMDb_BACKDROP_PATH + detail.backdropPath
                    tvOriginalTitle.text = detail.originalTitle
                    tvPopularity.text = detail.voteAverage.toString()
                    tvReleaseDate.text = detail.releaseDate
                    tvDescriptionOverview.text = detail.overview
                    getFavorite(detail.id.toString())
                    ivCart.setOnClickListener {
                        detail.backdropPath?.let { it1 ->
                            detail.posterPath?.let { it2 ->
                                addCart(detail.id,
                                    it1, detail.originalLanguage, detail.originalTitle, detail.overview, binding.tvPopularity.text.toString(),
                                    it2, detail.releaseDate, false)
                            }
                        }
                    }
                    ivWishlist.setOnClickListener {
                        detail.backdropPath?.let { it1 ->
                            detail.posterPath?.let { it2 ->
                                addWishlist(detail.id,
                                    it1, detail.originalLanguage, detail.originalTitle, detail.overview, binding.tvPopularity.text.toString().toInt().toDouble(),
                                    it2, detail.releaseDate, false)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addCart(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: String,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean
    ){
        viewModel.addCart(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)
        Toast.makeText(context,resources.getString(R.string.string_success_added_to_cart), Toast.LENGTH_SHORT).show()
    }

    private fun addWishlist(
        id: Int,
        backdropPath: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: Double,
        posterPath: String,
        releaseDate: String,
        isChecked: Boolean
    ){
        viewModelWishlist.addWishlist(id, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, isChecked)
        Toast.makeText(context, resources.getString(R.string.string_success_added_wishlist), Toast.LENGTH_SHORT).show()
    }

    private fun updateToken(){
        viewModel.getTokenBalance()
        viewModel.isTokenBalance.observe(viewLifecycleOwner){
            if (it.toInt() < binding.tvPopularity.text.toString().toInt()){
                binding.btnBuy.isEnabled = false
                val color = ContextCompat.getColor(requireContext(), R.color.colorGray)
                binding.btnBuy.backgroundTintList = ColorStateList.valueOf(color)
            }else{
                binding.btnBuy.isEnabled = true
                val color = ContextCompat.getColor(requireContext(), R.color.colorYellow)
                binding.btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                if (it != ""){
                    val result =  it.toInt() - binding.tvPopularity.text.toString().toInt()
                    addToken(result.toString())
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val bun = Bundle()
        bun.apply { putString(resources.getString(R.string.string_screenname), resources.getString(R.string.string_detail_movie)) }
        analyticsFirebase(resources.getString(R.string.string_checkout), bun)
    }

    private fun analyticsFirebase(message : String, bundle : Bundle){
        viewModel.logEvent(message, bundle)
        viewModel.debugSreenView(message)
    }
    private fun addToken(token : String){
        viewModel.saveTokenBalance(token)
    }


    private fun getFavorite(id: String){
        viewModel.getFavoriteById(id)
        viewModelWishlist.getFavoriteById(id)
        viewModel.getIsCartKey.observe(viewLifecycleOwner){
            with(binding){
                when(it.size){
                    1 -> {
                        ivCart.load(R.drawable.ic_bookmark_full)
                        ivCart.isClickable = false
                    }
                    else ->{
                        ivCart.load(R.drawable.ic_wishlist)
                        ivCart.isClickable = true
                    }
                }
            }
        }

        viewModelWishlist.getIsWishlistKey.observe(viewLifecycleOwner){
            with(binding){
                when(it.size){
                    1 -> {
                        ivWishlist.resources.getColor(R.color.colorGray)
                        ivWishlist.isClickable = false
                    }
                    else ->{
                        ivWishlist.resources.getColor(R.color.white)
                        ivWishlist.isClickable = true
                    }
                }
            }
        }
    }
}