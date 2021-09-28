package com.example.network

import com.example.model.NewsList
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface Webservice {
    @GET("top-headlines")
    suspend fun getNewsHeadlines(@Query("country") country:String) : NewsList
}