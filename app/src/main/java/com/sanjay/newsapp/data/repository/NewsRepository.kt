package com.sanjay.newsapp.data.repository

import com.sanjay.newsapp.data.api.ApiHelper
import com.sanjay.newsapp.data.model.NewsModel
import com.sanjay.newsapp.utils.NetworkHelper
import com.sanjay.newsapp.utils.Resource
import com.sanjay.newsapp.utils.ResponseHandler
import com.sanjay.newsapp.utils.Utility
import retrofit2.Response

class NewsRepository(
    private val apiHelper: ApiHelper,
    private val responseHandler: ResponseHandler,
    private val networkHelper: NetworkHelper
) {

    suspend fun getNewsData(
        key: String
    ): Resource<Response<NewsModel>> {
        return try {
            if (networkHelper.isNetworkConnected()) {
                val response = apiHelper.getNewsData(key)
                if (response.isSuccessful) {
                    return responseHandler.handleSuccess(response)
                } else {
                    val message = if (response.code() == 500) {
                        "Something went wrong"
                    } else {
                        response.errorBody()?.string()?.let { errorBody ->
                            Utility.getErrorResponseMessage(
                                errorBody
                            )
                        }.toString()
                    }
                    return responseHandler.handleException(java.lang.Exception(message))
                }
            } else {
                return responseHandler.handleNoNetworkException(java.lang.Exception("No Network Connection"))
            }
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}