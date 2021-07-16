package com.sanjay.newsapp.ui

import android.app.Application
import android.content.Context
import com.sanjay.newsapp.di.NewsModule
import com.sanjay.newsapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        initLibraries()
    }

    private fun initLibraries() {
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    appModule,
                    NewsModule
                )
            )
        }
    }

    companion object {
        private var context: Context? = null

        fun getContext(): Context? {
            return context
        }
    }
}