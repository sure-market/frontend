package com.example.sure_market.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providePostRepository(): PostRepository = PostRepository()

    @Provides
    fun provideUserRepository(): UserRepository = UserRepository()
}