package id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import id.rifqipadisiliwangi.core.domain.paging.search.SearchPagingSource
import id.rifqipadisiliwangi.core.domain.usecase.MovieInteractor
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.search.AdapterSearchItem
import kotlinx.coroutines.launch

class SearchViewModel(private val interactor: MovieInteractor): ViewModel() {

    val searchList = Pager(PagingConfig(pageSize = 4)) {
        SearchPagingSource(interactor)
    }.liveData.cachedIn(viewModelScope)

    fun getListSearchPlaying(adapter: AdapterSearchItem, query: String, lang: String) {
        viewModelScope.launch {
            val response = interactor.searchMovie(
                query = query,
                lang = lang,
                page  = 1
            )
            response.let {
                val store = it.results
                adapter.submitData(PagingData.from(store))
            }
        }
    }
}