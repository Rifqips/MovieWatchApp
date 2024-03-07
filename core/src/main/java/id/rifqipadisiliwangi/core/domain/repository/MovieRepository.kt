package id.rifqipadisiliwangi.core.domain.repository

import androidx.paging.PagingData
import id.rifqipadisiliwangi.core.data.datasource.MovieDataSourceImpl
import id.rifqipadisiliwangi.core.data.datasource.PagingDataSource
import id.rifqipadisiliwangi.core.data.local.database.movie.MovieKey
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResponse
import id.rifqipadisiliwangi.core.domain.model.popular.PopularResponse
import id.rifqipadisiliwangi.core.domain.model.search.SearchItemResponse
import id.rifqipadisiliwangi.core.utils.DataMapper.toNowPlayingMapper
import id.rifqipadisiliwangi.core.utils.DataMapper.toPopularMapper
import id.rifqipadisiliwangi.core.utils.DataMapper.toSearchMapper
import id.rifqipadisiliwangi.core.utils.safeDataCall
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

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
     ): PopularResponse

    suspend fun fetchProductsLocal(): Flow<PagingData<MovieKey>>

}

class MovieRepositoryImpl(private val datasource : MovieDataSourceImpl, private val paging: PagingDataSource) : MovieRepository {

    override suspend fun getNowPlaying(lang: String?, pageItem: Int?): NowPlayingResponse {
        return datasource.getNowPlaying(lang = lang, pageItem = pageItem).toNowPlayingMapper()
    }

    override suspend fun searchMovie(
        query: String?,
        lang: String?,
        page: Int?
    ): SearchItemResponse {
        return datasource.searchMovie(query, lang, page).toSearchMapper()
    }

    override suspend fun getPopular(lang: String?, pageItem: Int?): PopularResponse = safeDataCall {
        val data = datasource.getPopular(lang = lang, pageItem = pageItem).toPopularMapper()
        data
    }

    override suspend fun fetchProductsLocal(): Flow<PagingData<MovieKey>> = safeDataCall {
        paging.fetchProducts()
    }
}