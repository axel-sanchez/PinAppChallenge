package com.example.pinappchallenge.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.models.Post

/**
 * @author Axel Sanchez
 */
@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    suspend fun getAllPosts(): List<Post?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post): Long
    @Query("SELECT * FROM Comment where postId = :idPost")
    suspend fun getAllComments(idPost: Int): List<Comment?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment): Long
}