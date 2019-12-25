package com.github.catomizer.di

import android.content.Context

object ComponentManager {
    lateinit var appComponent: AppComponent

    fun initComponents(appContext: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(appContext))
            .navigationModule(NavigationModule())
            .networkModule(NetworkModule())
            .build()
    }
}