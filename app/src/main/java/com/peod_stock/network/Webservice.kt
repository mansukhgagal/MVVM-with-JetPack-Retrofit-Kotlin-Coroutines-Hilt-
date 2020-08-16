package com.peod_stock.network

import com.peod_stock.model.ApiUser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

@JvmSuppressWildcards
interface Webservice {
    @GET("users")
    suspend fun getUsers(): List<ApiUser>
}