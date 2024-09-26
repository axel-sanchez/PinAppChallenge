package com.example.pinappchallenge.domain.usecase

import com.example.pinappchallenge.data.models.DataComments
import com.example.pinappchallenge.domain.repository.CommentRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetAllCommentsUseCase{
    suspend fun call(idPost: Int): DataComments
}

@Singleton
class GetAllCommentsUseCaseImpl @Inject constructor(private val repository: CommentRepository):
    GetAllCommentsUseCase {
    override suspend fun call(idPost: Int): DataComments {
        return repository.getAllComments(idPost)
    }
}