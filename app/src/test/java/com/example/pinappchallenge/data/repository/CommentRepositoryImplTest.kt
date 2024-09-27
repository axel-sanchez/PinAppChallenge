package com.example.pinappchallenge.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pinappchallenge.data.models.DataComments
import com.example.pinappchallenge.domain.usecase.GetAllCommentsUseCase
import com.example.pinappchallenge.presentation.viewmodels.CommentViewModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class CommentRepositoryImplTest{
    private val repository = FakeCommentRepository()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun should_update_livedata_with_comment_list(){
        val useCase = object : GetAllCommentsUseCase {
            override suspend fun call(idPost: Int): DataComments {
                return repository.getAllComments(idPost)
            }
        }

        val viewModel = CommentViewModel(useCase)
        runBlocking {
            viewModel.setListData(useCase.call(1))
            val dataComments = viewModel.getCommentsLiveData().value
            dataComments?.results?.let { comments ->
                MatcherAssert.assertThat(comments, Matchers.contains(repository.comment1, repository.comment2, repository.comment3,
                    repository.comment4))
            } ?: kotlin.run { Assert.fail("El Live Data no pudo ser actualizado con su nuevo valor") }
        }
    }
}