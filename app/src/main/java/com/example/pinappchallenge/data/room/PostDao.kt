package com.example.pinappchallenge.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
}