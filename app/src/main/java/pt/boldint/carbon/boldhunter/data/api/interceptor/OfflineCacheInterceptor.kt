package pt.boldint.carbon.boldhunter.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.CacheControl
import pt.boldint.carbon.boldhunter.utils.NetworkUtils
import java.util.concurrent.TimeUnit

class OfflineCacheInterceptor(private val maxStaleDays: Int) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!NetworkUtils.isConnected()) {
            val cacheControl = CacheControl.Builder()
                    .maxStale(maxStaleDays, TimeUnit.DAYS)
                    .build()

            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
        }

        return chain.proceed(request)
    }
}