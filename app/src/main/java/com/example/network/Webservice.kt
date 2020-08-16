package com.example.network

import com.example.model.ApiUser
import retrofit2.http.GET

@JvmSuppressWildcards
interface Webservice {
    @GET("users")
    suspend fun getUsers(): List<ApiUser>
}