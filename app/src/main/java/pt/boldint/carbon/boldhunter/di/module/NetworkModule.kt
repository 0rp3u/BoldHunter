package pt.boldint.carbon.boldhunter.di.module

import pt.boldint.carbon.boldhunter.BoldHunterApp
import pt.boldint.carbon.boldhunter.data.api.interceptor.HttpAuthInterceptor

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import pt.boldint.carbon.boldhunter.data.api.HunterService
import pt.boldint.carbon.boldhunter.data.api.interceptor.CacheInterceptor
import pt.boldint.carbon.boldhunter.data.api.interceptor.OfflineCacheInterceptor

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val apiEndpointUri: String, private val token: String) {

    @Provides
    @Singleton
    fun provideCache(app: BoldHunterApp): Cache {
        val cacheSize = 40 * 1024 * 1024 // 40 MiB
        return Cache(app.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpAuthInterceptor(token))
            .addNetworkInterceptor(CacheInterceptor(2))
            .addInterceptor(OfflineCacheInterceptor(5))
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(apiEndpointUri)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideTollingService(retrofit: Retrofit): HunterService = retrofit.create(HunterService::class.java)

}