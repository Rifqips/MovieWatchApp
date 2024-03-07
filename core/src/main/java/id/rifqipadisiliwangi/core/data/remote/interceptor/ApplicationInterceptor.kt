package id.rifqipadisiliwangi.core.data.remote.interceptor


import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ApplicationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        runBlocking {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MWQ0MzM2YzIyMjg0NTQ0Zjg0Y2NkZDA2NDQ0Y2YxNyIsInN1YiI6IjYzMzc1NGE3MjU1ZGJhMDA4MTZhNGNhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8JFK2grwDrl2kBa93qPMFay205nx7xrUPjbyupjzAOU")
                .build()
        }
        return chain.proceed(request)
    }
}
