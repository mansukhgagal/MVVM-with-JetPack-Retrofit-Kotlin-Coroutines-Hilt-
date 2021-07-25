package com.example.network

import com.example.BuildConfig
import com.example.base.MyApp
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L

    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val myCache = Cache(MyApp.instance.cacheDir, cacheSize)

    val retrofitBuilder: Retrofit.Builder by lazy {

        val client = OkHttpClient.Builder()
            .cache(myCache)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(NetworkInterceptor())


        if(BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

      Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client.build())
    }

    val apiService: Webservice by lazy {
        retrofitBuilder.build().create(Webservice::class.java)
    }
}