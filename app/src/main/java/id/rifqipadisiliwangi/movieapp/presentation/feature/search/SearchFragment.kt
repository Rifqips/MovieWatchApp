package id.rifqipadisiliwangi.movieapp.presentation.feature.search

import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.domain.model.search.SearchItemResult
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentSearchBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.load.LoadStateAdapterNowPlaying
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.search.AdapterSearchItem
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.search.SearchViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant.SEARCH
import id.rifqipadisiliwangi.movieapp.utils.Constant.lang_request
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(FragmentSearchBinding::inflate) {

    override val viewModel: SearchViewModel by viewModel()

    private val adapterSearch by lazy {
        AdapterSearchItem{ navigateToDetail(it) }
    }

    override fun initView() {
        adapterSearch.addLoadStateListener { loadState -> with(binding){ rvSearchItem.isInvisible = loadState.refresh is LoadState.Loading } }
        adapterSearch.withLoadStateFooter( footer = LoadStateAdapterNowPlaying { adapterSearch.retry() } )
        with(binding){
            rvSearchItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterSearch
                postDelayed({ rvSearchItem.smoothScrollToPosition(0) }, 1000)
            }
        }
    }

    override fun initListener() {
        with(binding) {
            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchItem(searchView.text.toString())
                    true
                }
        }
    }

    override fun observeData() {
        if (view != null){
            parentFragment?.viewLifecycleOwner?.let {
                viewModel.searchList.observe(it) { pagingData ->
                    adapterSearch.submitData(lifecycle,pagingData)
                }
            }
        }
    }

    private fun searchItem(search : String){
        viewModel.getListSearchPlaying(adapterSearch, search, lang_request)
    }

    private fun navigateToDetail(searchItem : SearchItemResult){
        val bundle = Bundle()
        bundle.putParcelable(SEARCH, searchItem)
        activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_searchFragment_to_detailFragment, bundle)
    }
}