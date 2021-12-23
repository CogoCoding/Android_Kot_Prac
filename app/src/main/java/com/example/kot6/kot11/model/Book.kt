package com.example.kot6.kot11.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book (
    @SerializedName("itemID") val id:Long,
    @SerializedName("title") val title:String,
    @SerializedName("description") val description:String,
    @SerializedName("coverSmallUrl") val coverSmallUrl:String
): Parcelable //gradle-plugin에 추가해서 직렬화
