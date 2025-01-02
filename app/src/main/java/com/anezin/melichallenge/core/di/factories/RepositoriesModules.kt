package com.anezin.melichallenge.core.di.factories

import com.anezin.melichallenge.core.interfaces.repositories.SearchProductRepository
import com.anezin.melichallenge.infrastructure.repositories.HttpSearchProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindSearchProductRepository(
        implementation: HttpSearchProductRepository
    ): SearchProductRepository
}