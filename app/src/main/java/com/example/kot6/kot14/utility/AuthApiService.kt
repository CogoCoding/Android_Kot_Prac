package com.example.kot6.kot14.utility

import com.example.kot6.kot14.data.response.GithubAccessTokenResponse
import retrofit2.Response//retrofit으로 import
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")

    suspend fun getAccessToken(
        @Field("client_id")clientId:String,
        @Field("client_secret")clientSecret:String,
        @Field("code")code:String
    ): retrofit2.Response<GithubAccessTokenResponse>
}