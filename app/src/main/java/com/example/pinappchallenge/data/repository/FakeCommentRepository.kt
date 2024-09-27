package com.example.pinappchallenge.data.repository

import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.models.DataComments
import com.example.pinappchallenge.data.models.Post
import com.example.pinappchallenge.domain.repository.CommentRepository

/**
 * @author Axel Sanchez
 */
class FakeCommentRepository: CommentRepository {

    val comment1 = Comment(1, "", "", "", 1)
    val comment2 = Comment(2, "", "", "", 1)
    val comment3 = Comment(3, "", "", "", 1)
    val comment4 = Comment(4, "", "", "", 1)

    val dataComments = DataComments(results = listOf(comment1, comment2, comment3, comment4))

    override suspend fun getAllComments(idPost: Int) = dataComments

    override suspend fun getRemoteComments(idPost: Int) = dataComments

    override suspend fun getLocalComments(idPost: Int): List<Comment?> {
        return listOf()
    }
}