package com.sanjay.newsapp.data.api

import com.sanjay.newsapp.data.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNewsData(
        @Query("country") page: String,
        @Query("apiKey") key: String
    ): Response<NewsModel>

}