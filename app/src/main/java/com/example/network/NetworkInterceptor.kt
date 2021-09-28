package com.example.network

import com.example.utils.Constant
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class NetworkInterceptor : Interceptor/*, Authenticator*/ {
    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

//        val currentToken = "token"

        //Intercept and Add common API KEY in query string
        //Intercept and Add common API KEY in query string
        val originalHttpUrl: HttpUrl = request.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", Constant.API_KEY)
            .build()

        //Add common headers
        request = request.newBuilder()
            .url(url)
            .build()
//            .addHeader("token",currentToken)
//            .addHeader("key", UUID.randomUUID().toString())

        val response = chain.proceed(request)

        /*if (response.code() == 401) {
            synchronized(TAG) {

            }
        }*/

        return response
    }

    companion object {
        private const val TAG = "NetworkInterceptor"
    }
}