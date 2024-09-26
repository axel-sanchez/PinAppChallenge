package com.example.pinappchallenge.data.source

import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.room.PostDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface CommentLocalSource {
    suspend fun getAllComments(idPost: Int): List<Comment?>
    suspend fun insertComment(comment: Comment)
}
@Singleton
class CommentLocalSourceImpl @Inject constructor(private val database: PostDao): CommentLocalSource{
    override suspend fun getAllComments(idPost: Int): List<Comment?> {
        return database.getAllComments(idPost)
    }

    override suspend fun insertComment(comment: Comment) {
        database.insertComment(comment)
    }
}