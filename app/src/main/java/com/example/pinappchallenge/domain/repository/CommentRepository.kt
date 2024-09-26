package com.example.pinappchallenge.domain.repository

import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.models.DataComments

/**
 * @author Axel Sanchez
 */
interface CommentRepository {
    suspend fun getAllComments(idPost: Int): DataComments
    suspend fun getRemoteComments(idPost: Int): DataComments
    suspend fun getLocalComments(idPost: Int): List<Comment?>
}