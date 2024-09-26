package com.example.pinappchallenge.data.repository

import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.models.DataComments
import com.example.pinappchallenge.data.source.CommentLocalSource
import com.example.pinappchallenge.data.source.CommentRemoteSource
import com.example.pinappchallenge.domain.repository.CommentRepository
import com.example.pinappchallenge.helpers.Constants
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
class CommentRepositoryImpl @Inject constructor(
    private val commentRemoteSource: CommentRemoteSource,
    private val commentLocalSource: CommentLocalSource
): CommentRepository {
    override suspend fun getAllComments(idPost: Int): DataComments {
        val localComments = getLocalComments(idPost)
        if (localComments.isNotEmpty()) {
            return DataComments(results = localComments)
        }

        val remoteDataComments = getRemoteComments(idPost)

        if (!remoteDataComments.results.isNullOrEmpty()) {
            addCommentInDB(remoteDataComments.results)
        }

        return remoteDataComments
    }

    override suspend fun getRemoteComments(idPost: Int): DataComments {
        return commentRemoteSource.getAllComments(idPost).value ?: DataComments(apiError = Constants.ApiError.GENERIC)
    }

    override suspend fun getLocalComments(idPost: Int): List<Comment?> {
        return commentLocalSource.getAllComments(idPost)
    }

    private suspend fun addCommentInDB(result: List<Comment?>) {
        result.forEach { comment ->
            comment?.let {
                commentLocalSource.insertComment(it)
            }
        }
    }
}