package com.example.repository

import com.example.model.NewsList
import com.example.network.Webservice
import com.example.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class NewsRepository @Inject constructor(val api: Webservice) {

    suspend fun getNewsHeadLines(country:String?=null)  : Resource<NewsList> {
        Timber.d("getNewsHeadlines")
        val response = try {
            api.getNewsHeadlines(country ?: "us")
        } catch (e:Exception) {
            return Resource.Error("An unknown error has occurred.")
        }
        return Resource.Success(response)
    }

}