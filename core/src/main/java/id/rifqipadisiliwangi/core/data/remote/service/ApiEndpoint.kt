package id.rifqipadisiliwangi.core.data.remote.service

import id.rifqipadisiliwangi.core.data.remote.data.nowplaying.ResponseNowPlaying
import id.rifqipadisiliwangi.core.data.remote.data.popular.ResponsePopular
import id.rifqipadisiliwangi.core.data.remote.data.search.ResponseSearchItem
import id.rifqipadisiliwangi.core.utils.Constant.NOW_PLAYING
import id.rifqipadisiliwangi.core.utils.Constant.POPULAR
import id.rifqipadisiliwangi.core.utils.Constant.SEARCH
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {

    @GET(NOW_PLAYING)
    suspend fun getNowPlaying(
        @Query("language") lang:String? = null,
        @Query("page") page:Int? = null,
    ): ResponseNowPlaying

    @GET(POPULAR)
    suspend fun getPopular(
        @Query("language") lang:String? = null,
        @Query("page") page:Int? = null,
    ): ResponsePopular

    @GET(SEARCH)
    suspend fun searchMovie(
        @Query("query") query:String? = null,
        @Query("language") lang:String? = null,
        @Query("page") page:Int? = null,
    ) : ResponseSearchItem
}