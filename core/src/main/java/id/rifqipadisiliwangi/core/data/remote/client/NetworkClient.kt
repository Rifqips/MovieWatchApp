package id.rifqipadisiliwangi.core.data.remote.client

import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.rifqipadisiliwangi.core.BuildConfig
import id.rifqipadisiliwangi.core.data.remote.interceptor.ApplicationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient (
    val authInterceptor: ApplicationInterceptor,
    val chuckerInterceptor: ChuckerInterceptor
){
    inline fun <reified I> create():I{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(I::class.java)
    }
}