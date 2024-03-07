package id.rifqipadisiliwangi.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import id.rifqipadisiliwangi.core.data.local.database.movie.MovieKey
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResponse
import id.rifqipadisiliwangi.core.domain.model.popular.PopularResult
import id.rifqipadisiliwangi.core.domain.model.search.SearchItemResponse
import id.rifqipadisiliwangi.core.domain.repository.MovieRepository
import id.rifqipadisiliwangi.core.domain.state.UiState
import id.rifqipadisiliwangi.core.utils.DataMapper.toListResultPopular
import id.rifqipadisiliwangi.core.utils.DataMapper.toLocaleUiData
import id.rifqipadisiliwangi.core.utils.safeDataCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

interface MovieUseCase {

    suspend fun getNowPlaying(
        lang:String? = null,
        pageItem:Int? = null,
    ): NowPlayingResponse

    suspend fun searchMovie(
        query:String? = null,
        lang:String? = null,
        page:Int? = null,
    ) : SearchItemResponse

    suspend fun getPopular(
        lang:String? = null,
        pageItem:Int? = null
    ): List<PopularResult>

    suspend fun fetchLocalProducts(): Flow<UiState<PagingData<PopularResult>>>
}

class MovieInteractor(private val repository : MovieRepository) : MovieUseCase {


    override suspend fun getNowPlaying(
        lang: String?,
        pageItem: Int?
    ): NowPlayingResponse {
        return  repository.getNowPlaying(lang, pageItem)
    }

    override suspend fun searchMovie(
        query: String?,
        lang: String?,
        page: Int?
    ): SearchItemResponse {
        return repository.searchMovie(query, lang, page)
    }

    override suspend fun getPopular(
        lang: String?,
        pageItem: Int?
    ): List<PopularResult> = safeDataCall {
        repository.getPopular(lang, pageItem).results.toListResultPopular()
    }

    override suspend fun fetchLocalProducts(): Flow<UiState<PagingData<PopularResult>>> = safeDataCall{
        repository.fetchProductsLocal().map { data ->
            val mapped = data.map { productEntity: MovieKey ->
                productEntity.toLocaleUiData()
            }
            UiState.Success(mapped)

        }.flowOn(Dispatchers.IO).catch { throwable -> UiState.Error(throwable) }
    }
}