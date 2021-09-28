package com.example.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticle(
    @SerializedName("author")
    val author: String? = "-",
    @SerializedName("title")
    val title: String? = "-",
    @SerializedName("description")
    val description: String? = "-",
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("urlToImage")
    val urlToImage: String? = null,
    @SerializedName("publishedAt")
    val publishedAt: String? = "-",
    @SerializedName("content")
    val content: String? = "-",
    @SerializedName("source")
    val source: Source? = null

) : Parcelable

