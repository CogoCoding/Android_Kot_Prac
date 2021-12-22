package com.example.kot6.kot11.model

import com.google.gson.annotations.SerializedName


data class Book (
    @SerializedName("itemID") val id:Long,
    @SerializedName("title") val title:String,
    @SerializedName("description") val description:String,
    @SerializedName("coverSmallUrl") val coverSmallUrl:String
)
