package com.example.pinappchallenge.domain.repository

import com.example.pinappchallenge.data.models.DataPosts
import com.example.pinappchallenge.data.models.Post

/**
 * @author Axel Sanchez
 */
interface PostRepository {
    suspend fun getAllPosts(): DataPosts
    suspend fun getRemotePosts(): DataPosts
    suspend fun getLocalPosts(): List<Post?>
}