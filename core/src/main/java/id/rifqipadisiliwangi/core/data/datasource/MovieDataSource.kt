package id.rifqipadisiliwangi.core.data.datasource

import id.rifqipadisiliwangi.core.data.remote.data.nowplaying.ResponseNowPlaying
import id.rifqipadisiliwangi.core.data.remote.data.popular.ResponsePopular
import id.rifqipadisiliwangi.core.data.remote.data.search.ResponseSearchItem
import id.rifqipadisiliwangi.core.data.remote.service.ApiEndpoint
import id.rifqipadisiliwangi.core.utils.safeApiCall

interface MovieDataSource {
    suspend fun getNowPlaying(
        lang:String? = null,
        pageItem:Int? = null,
    ): ResponseNowPlaying

    suspend fun searchMovie(
        query:String? = null,
        lang:String? = null,
        page:Int? = null,
    ) : ResponseSearchItem

    suspend fun getPopular(
        lang:String? = null,
        pageItem:Int? = null,
    ): ResponsePopular

}
class MovieDataSourceImpl(private val api : ApiEndpoint) : MovieDataSource {

    override suspend fun getNowPlaying(lang: String?, pageItem: Int?): ResponseNowPlaying {
        return api.getNowPlaying(lang = lang, page = pageItem)
    }

    override suspend fun searchMovie(query: String?, lang: String?, page: Int?): ResponseSearchItem {
        return api.searchMovie(query, lang, page)
    }

    override suspend fun getPopular(lang: String?, pageItem: Int?): ResponsePopular =  safeApiCall { api.getPopular(lang = lang, page = pageItem) }
}