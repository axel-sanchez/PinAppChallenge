package com.example.pinappchallenge.data.repository

import com.example.pinappchallenge.data.models.DataPosts
import com.example.pinappchallenge.data.models.Post
import com.example.pinappchallenge.domain.repository.PostRepository

/**
 * @author Axel Sanchez
 */
class FakePostRepository: PostRepository {

    val post1 = Post(1, "", "", 1)
    val post2 = Post(2, "", "", 1)
    val post3 = Post(3, "", "", 1)
    val post4 = Post(4, "", "", 1)

    val dataPosts = DataPosts(results = listOf(post1, post2, post3, post4))

    override suspend fun getAllPosts() = dataPosts

    override suspend fun getRemotePosts() = dataPosts

    override suspend fun getLocalPosts(): List<Post?> {
        return listOf()
    }
}