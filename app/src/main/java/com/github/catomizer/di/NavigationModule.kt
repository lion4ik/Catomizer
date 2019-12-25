package com.github.catomizer.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton


@Module
class NavigationModule {
    private var cicerone: Cicerone<Router> = Cicerone.create<Router>(Router())

    @Provides
    @Singleton
    fun provideRouter(): Router = cicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder= cicerone.navigatorHolder

}