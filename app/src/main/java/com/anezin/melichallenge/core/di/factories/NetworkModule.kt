package com.anezin.melichallenge.core.di.factories

import com.anezin.melichallenge.infrastructure.http.RetrofitProductClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.mercadolibre.com/sites/MLA/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductClient(retrofit: Retrofit): RetrofitProductClient {
        return retrofit.create(RetrofitProductClient::class.java)
    }
}