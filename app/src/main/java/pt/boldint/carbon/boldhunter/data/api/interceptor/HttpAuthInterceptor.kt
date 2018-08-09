package pt.boldint.carbon.boldhunter.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class HttpAuthInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
                .let { chain.proceed(it)}

    }
}