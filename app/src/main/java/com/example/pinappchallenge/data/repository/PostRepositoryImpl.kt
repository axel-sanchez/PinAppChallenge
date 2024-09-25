package com.example.pinappchallenge.data.repository

import com.example.pinappchallenge.data.models.DataPost
import com.example.pinappchallenge.data.models.Post
import com.example.pinappchallenge.data.source.PostLocalSource
import com.example.pinappchallenge.data.source.PostRemoteSource
import com.example.pinappchallenge.domain.repository.PostRepository
import com.example.pinappchallenge.helpers.Constants
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
class PostRepositoryImpl @Inject constructor(
    private val postRemoteSource: PostRemoteSource,
    private val postLocalSource: PostLocalSource
): PostRepository {
    override suspend fun getAllPosts(): DataPost {
        val localCharacters = getLocalPosts()
        if (localCharacters.isNotEmpty()) {
            return DataPost(results = localCharacters)
        }

        val remoteDataCharacters = getRemotePosts()

        if (!remoteDataCharacters.results.isNullOrEmpty()) {
            addCharacterInDB(remoteDataCharacters.results)
        }

        return remoteDataCharacters
    }

    override suspend fun getRemotePosts(): DataPost {
        return postRemoteSource.getAllPosts().value ?: DataPost(apiError = Constants.ApiError.GENERIC)
    }

    override suspend fun getLocalPosts(): List<Post?> {
        return postLocalSource.getAllPosts()
    }

    private suspend fun addCharacterInDB(result: List<Post?>) {
        result.forEach { character ->
            character?.let {
                postLocalSource.insertPost(it)
            }
        }
    }
}