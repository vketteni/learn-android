package com.example.learn

import android.app.Application
import com.example.learn.data.AppContainer
import com.example.learn.data.AppDataContainer

class LearnApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}