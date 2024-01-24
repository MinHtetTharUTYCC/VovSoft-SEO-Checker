package com.example.bookshellf

import android.app.Application
import com.example.bookshellf.di.AppContainer
import com.example.bookshellf.di.DefaultAppContainer

class BookshelfApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}