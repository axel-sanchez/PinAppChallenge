package com.example.pinappchallenge.data.source

import com.example.pinappchallenge.data.models.Post
import com.example.pinappchallenge.data.room.PostDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface PostLocalSource {
    suspend fun getAllPosts(): List<Post?>
    suspend fun insertPost(post: Post)
}
@Singleton
class PostLocalSourceImpl @Inject constructor(private val database: PostDao): PostLocalSource{
    override suspend fun getAllPosts(): List<Post?> {
        return database.getAllPosts()
    }

    override suspend fun insertPost(post: Post) {
        database.insertPost(post)
    }

}