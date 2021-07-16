package com.sanjay.newsapp.di

import com.sanjay.newsapp.data.repository.NewsRepository
import com.sanjay.newsapp.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val NewsModule = module {
    factory { NewsRepository(get(), get(), get()) }
    viewModel { NewsViewModel(get()) }

}