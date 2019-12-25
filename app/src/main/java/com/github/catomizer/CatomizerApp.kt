package com.github.catomizer

import android.app.Application
import com.github.catomizer.di.ComponentManager

class CatomizerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentManager.initComponents(applicationContext)
    }
}