package com.example.di

import com.example.BuildConfig
import com.example.base.MyApp
import com.example.network.NetworkInterceptor
import com.example.network.Webservice
import com.example.repository.NewsRepository
import com.example.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = Constant.TOP_HEADLINE_URL
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L

    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val myCache = Cache(MyApp.instance.cacheDir, cacheSize)

    @Singleton
    @Provides
    fun provideNewsRepository(api:Webservice) = NewsRepository(api)

    @Singleton
    @Provides
    fun provideNewsApi(): Webservice {

        val client = OkHttpClient.Builder()
            .cache(myCache)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(NetworkInterceptor())

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addNetworkInterceptor(logging)
        }

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client.build())
            .build().create(Webservice::class.java)

    }
}