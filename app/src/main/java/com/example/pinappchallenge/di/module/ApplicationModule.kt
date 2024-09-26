package com.example.pinappchallenge.di.module

import android.content.Context
import androidx.room.Room
import com.example.pinappchallenge.data.repository.CommentRepositoryImpl
import com.example.pinappchallenge.data.repository.PostRepositoryImpl
import com.example.pinappchallenge.data.room.Database
import com.example.pinappchallenge.data.service.ApiClient
import com.example.pinappchallenge.data.service.ApiServicePost
import com.example.pinappchallenge.data.source.*
import com.example.pinappchallenge.domain.repository.CommentRepository
import com.example.pinappchallenge.domain.repository.PostRepository
import com.example.pinappchallenge.domain.usecase.GetAllCommentsUseCase
import com.example.pinappchallenge.domain.usecase.GetAllCommentsUseCaseImpl
import com.example.pinappchallenge.domain.usecase.GetAllPostsUseCase
import com.example.pinappchallenge.domain.usecase.GetAllPostsUseCaseImpl
import com.example.pinappchallenge.helpers.Constants.BASE_URL
import com.example.pinappchallenge.helpers.NetworkHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Module
class ApplicationModule(private val context: Context){
    @Provides
    @Singleton
    fun providePostRepository(postLocalSource: PostLocalSource, postRemoteSource: PostRemoteSource): PostRepository {
        return PostRepositoryImpl(postRemoteSource, postLocalSource)
    }

    @Provides
    @Singleton
    fun providePostRemoteSource(postRemoteSource: PostRemoteSourceImpl): PostRemoteSource = postRemoteSource

    @Provides
    @Singleton
    fun provideCommentRepository(commentLocalSource: CommentLocalSource, commentRemoteSource: CommentRemoteSource): CommentRepository {
        return CommentRepositoryImpl(commentRemoteSource, commentLocalSource)
    }

    @Provides
    @Singleton
    fun provideCommentRemoteSource(commentRemoteSource: CommentRemoteSourceImpl): CommentRemoteSource = commentRemoteSource

    @Provides
    @Singleton
    fun provideGetAllPostsUseCase(getAllPostsUseCase: GetAllPostsUseCaseImpl): GetAllPostsUseCase = getAllPostsUseCase

    @Provides
    @Singleton
    fun provideGetAllCommentsUseCase(getAllCommentsUseCase: GetAllCommentsUseCaseImpl): GetAllCommentsUseCase = getAllCommentsUseCase

    @Provides
    @Singleton
    fun provideApiServiceCharacter(): ApiServicePost {
        return ApiClient.Builder<ApiServicePost>()
            .setBaseUrl(BASE_URL)
            .setApiService(ApiServicePost::class.java)
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, "PinAppDB.db2")
            .build()
    }

    @Provides
    @Singleton
    fun providePostLocalSource(database: Database): PostLocalSource {
        return PostLocalSourceImpl(database.postDao())
    }
    @Provides
    @Singleton
    fun provideCommentLocalSource(database: Database): CommentLocalSource {
        return CommentLocalSourceImpl(database.postDao())
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    @Singleton
    fun provideContext(): Context = context
}