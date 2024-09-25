package com.example.pinappchallenge.domain.repository

import com.example.pinappchallenge.data.models.DataPost
import com.example.pinappchallenge.data.models.Post

/**
 * @author Axel Sanchez
 */
interface PostRepository {
    suspend fun getAllPosts(): DataPost
    suspend fun getRemotePosts(): DataPost
    suspend fun getLocalPosts(): List<Post?>
}