package com.example.kot6.kot11.model

import com.google.gson.annotations.SerializedName

class BestSellerDto(
    @SerializedName("title") val title:String,
    @SerializedName("item") val books:List<Book>,


)
