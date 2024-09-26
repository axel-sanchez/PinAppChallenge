package com.example.pinappchallenge.domain.usecase

import com.example.pinappchallenge.data.models.DataPosts
import com.example.pinappchallenge.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetAllPostsUseCase{
    suspend fun call(): DataPosts
}

@Singleton
class GetAllPostsUseCaseImpl @Inject constructor(private val repository: PostRepository):
    GetAllPostsUseCase {
    override suspend fun call(): DataPosts {
        return repository.getAllPosts()
    }
}