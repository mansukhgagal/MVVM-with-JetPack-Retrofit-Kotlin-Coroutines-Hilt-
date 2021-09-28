package com.example.model

import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("status")
    val status:Boolean,
    @SerializedName("totalResults")
    val totalResults:Int,
    @SerializedName("articles")
    val article: List<NewsArticle> = emptyList()
)