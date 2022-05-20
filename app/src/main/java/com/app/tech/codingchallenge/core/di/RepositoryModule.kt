package com.app.tech.codingchallenge.core.di

import com.app.tech.codingchallenge.core.data.db.dao.CommentDao
import com.app.tech.codingchallenge.core.data.db.dao.PostDao
import com.app.tech.codingchallenge.core.network.ApiRepository
import com.app.tech.codingchallenge.core.network.ApiService
import com.app.tech.codingchallenge.core.utils.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideApiRepository(
        service: ApiService,
        networkHandler: NetworkHandler,
        postDao: PostDao,
        commentDao: CommentDao
    ): ApiRepository {
        return ApiRepository.ApiRepositoryImpl(
            service, postDao, commentDao, networkHandler
        )
    }
}