package com.example.kot6.kot11.model

import com.google.gson.annotations.SerializedName

data class SearchBookDto(
    @SerializedName("title") val title:String,
    @SerializedName("books") val books:List<Book>,
)