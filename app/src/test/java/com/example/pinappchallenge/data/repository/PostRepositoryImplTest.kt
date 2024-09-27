package com.example.pinappchallenge.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pinappchallenge.data.models.DataPosts
import com.example.pinappchallenge.domain.usecase.GetAllPostsUseCase
import com.example.pinappchallenge.presentation.viewmodels.PostViewModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PostRepositoryImplTest{
    private val repository = FakePostRepository()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun should_update_livedata_with_post_list(){
        val useCase = object : GetAllPostsUseCase {
            override suspend fun call(): DataPosts {
                return repository.getAllPosts()
            }
        }

        val viewModel = PostViewModel(useCase)
        runBlocking {
            viewModel.setListData(useCase.call())
            val dataPosts = viewModel.getPostsLiveData().value
            dataPosts?.results?.let { posts ->
                MatcherAssert.assertThat(posts, Matchers.contains(repository.post1, repository.post2, repository.post3,
                    repository.post4))
            } ?: kotlin.run { Assert.fail("El Live Data no pudo ser actualizado con su nuevo valor") }
        }
    }
}