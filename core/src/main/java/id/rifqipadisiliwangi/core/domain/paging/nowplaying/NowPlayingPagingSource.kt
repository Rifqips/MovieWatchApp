package id.rifqipadisiliwangi.core.domain.paging.nowplaying

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResult
import id.rifqipadisiliwangi.core.domain.usecase.MovieInteractor

class NowPlayingPagingSource(
private val interactor: MovieInteractor,
) : PagingSource<Int, NowPlayingResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingResult> {
        try {
            val currentPage = params.key ?: 1

            val response = interactor.getNowPlaying(
                lang = "",
                pageItem = currentPage
            )
            response.let {
                val movie = it.results
                val prevKey = if (currentPage == 1) null else currentPage - 1
                val nextKey = if (currentPage == it.totalPages) null else currentPage + 1
                return LoadResult.Page(movie, prevKey, nextKey)
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, NowPlayingResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}