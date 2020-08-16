package com.peod_stock.network

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("results") val result: Any?
)