package com.github.catomizer.di

import android.content.Context
import com.github.catomizer.data.db.CatsDatabase
import com.github.catomizer.data.db.FavoriteCatsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCatsDatabase(appContext: Context): CatsDatabase = CatsDatabase.build(appContext)

    @Singleton
    @Provides
    fun providesFavoriteCatsDao(db: CatsDatabase): FavoriteCatsDao = db.favoriteCatsDao()
}