package com.github.catomizer.di

import com.github.catomizer.BuildConfig
import com.github.catomizer.error.NetworkErrorMapper
import com.github.catomizer.network.AuthInterceptor
import com.github.catomizer.network.CatApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideCatApi(retrofit: Retrofit): CatApi =
        retrofit.create(CatApi::class.java)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor =
        AuthInterceptor()

    @Singleton
    @Provides
    fun provideNetworkErrorMapper(): NetworkErrorMapper = NetworkErrorMapper()
}