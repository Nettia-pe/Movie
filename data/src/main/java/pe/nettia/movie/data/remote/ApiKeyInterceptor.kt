package pe.nettia.movie.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val requestBuilder = original.newBuilder().url(url)
        return chain.proceed(requestBuilder.build())
    }
} 