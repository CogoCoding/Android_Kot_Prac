package com.example.kot6.kot11.api

import com.example.kot6.kot11.model.BestSellerDto
import com.example.kot6.kot11.model.SearchBookDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("/api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey:String,
        @Query("query") keyword:String // 검색어
    ): Call<SearchBookDto>

    @GET("/api/search.api?output=json&key=80E1EF53A3AC56C2CB65689FC0F0EE8A6BBFBB223A0B687773A61BEE892B42E1&query=gg")
    fun getBooksByNamefull(): Call<SearchBookDto>

    @GET("/api/bestSeller.api?output=json&categoryId=100")
    fun getBestSellerBooks(
        @Query("key")apiKey:String
    ): Call<BestSellerDto>
}