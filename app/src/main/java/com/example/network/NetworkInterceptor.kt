package com.example.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class NetworkInterceptor : Interceptor/*, Authenticator*/ {
    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        //Add common headers
        request = request.newBuilder()
            .addHeader("key", UUID.randomUUID().toString())
            .build()

        var response = chain.proceed(request)


        return response
    }

}