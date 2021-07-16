package com.sanjay.newsapp.data.api

import com.sanjay.newsapp.data.model.NewsModel
import com.sanjay.newsapp.utils.Constants
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getNewsData(
        key: String
    ): Response<NewsModel> =
        apiService.getNewsData(Constants.DEFAULT_COUNTRY, key)
}