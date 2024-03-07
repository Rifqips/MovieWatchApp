package id.rifqipadisiliwangi.movieapp.presentation.feature.wishlist

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.domain.model.wishlist.KeyWishlist
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentWishlistBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.wishlist.WishlistAdapter
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.wishlist.WishListViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant.WISHLIST_DETAIL
import org.koin.androidx.viewmodel.ext.android.viewModel


class WishlistFragment : BaseFragment<FragmentWishlistBinding, WishListViewModel>(FragmentWishlistBinding::inflate) {

    override val viewModel: WishListViewModel by viewModel()
    private val adapterWishlist : WishlistAdapter by lazy {
        WishlistAdapter(emptyList(), viewModel){
            navigateToDetail(it)
        }
    }

    override fun initView() {
        with(binding){
            tvWishlistMovie.text = resources.getString(R.string.string_wishlist)
            ivBack.load(R.drawable.ic_back)
            tvContent.text = resources.getString(R.string.string_dummy_content_description)
            tvSubContent.text = resources.getString(R.string.string_dummy_lorem_ipsum)
        }
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observeData() {
        viewModel.getWishlistList()
        viewModel.wishList.observe(viewLifecycleOwner){ it ->
            binding.rvWishlit.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterWishlist
                adapterWishlist.setlistWishlist(it)
            }
        }
        binding.rvWishlit.postDelayed({
            binding.rvWishlit.smoothScrollToPosition(0)
        }, 1000)
    }
    private fun navigateToDetail(item : KeyWishlist){
        val bundle = Bundle()
        bundle.putParcelable(WISHLIST_DETAIL, item)
        activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_wishlistFragment_to_detailFragment, bundle)
    }
}