package com.sanjay.newsapp.data.api

import com.sanjay.newsapp.data.model.NewsModel
import retrofit2.Response

interface ApiHelper {
    suspend fun getNewsData(
        key: String
    ): Response<NewsModel>
}