package com.sanjay.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.newsapp.data.model.NewsModel
import com.sanjay.newsapp.data.repository.NewsRepository
import com.sanjay.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    fun getNewsData(
        key: String
    ): LiveData<Resource<Response<NewsModel>>> {

        val dataList = MutableLiveData<Resource<Response<NewsModel>>>()

        viewModelScope.launch {
            val responseList = repository.getNewsData(key)
            dataList.value = responseList
        }
        return dataList
    }
}