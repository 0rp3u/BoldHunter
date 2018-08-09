package pt.boldint.carbon.boldhunter.data.api.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
class ThrottlerInterceptor : Interceptor {

    private var throtleTime  = 0
    override fun intercept(chain: Interceptor.Chain): Response {
        if(throtleTime >0) {  //Will Block Okhttp dispatcher's thread
            Log.d(javaClass.name, "Thread ${Thread.currentThread().name} is going to be Block for $throtleTime seconds")
            Thread.sleep(1000L * throtleTime)
        }

        val request = chain.request()
        return chain.proceed(request).also {
            throtleTime= if (it.header("X-Rate-Limit-Remaining")?.toInt() ?: 900 < 5)
                 it.header("X-Rate-Limit-Reset")?.toInt() ?: 0
            else 0
        }
    }
}