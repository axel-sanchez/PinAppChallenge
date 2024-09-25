package com.example.pinappchallenge.data.service

import com.example.pinappchallenge.data.models.DataPost
import com.example.pinappchallenge.data.models.Post
import com.example.pinappchallenge.helpers.Constants.GET_POSTS
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author Axel Sanchez
 */
interface ApiServicePost {
    @GET(GET_POSTS)
    suspend fun getPosts(): Response<List<Post>?>
}