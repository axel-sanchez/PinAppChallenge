package com.example.pinappchallenge.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.pinappchallenge.data.repository.FakeCommentRepository.Companion.ID_POST
import com.example.pinappchallenge.data.source.CommentLocalSource
import com.example.pinappchallenge.data.source.CommentRemoteSource
import com.example.pinappchallenge.domain.repository.CommentRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.BDDMockito

class CommentRepositoryImplTest{
    private val fakeRepository = FakeCommentRepository()

    private val commentRemoteSource: CommentRemoteSource =
        BDDMockito.mock(CommentRemoteSource::class.java)
    private val commentLocalSource: CommentLocalSource =
        BDDMockito.mock(CommentLocalSource::class.java)
    private val commentRepository: CommentRepository = CommentRepositoryImpl(commentRemoteSource, commentLocalSource)

    @Test
    fun should_calls_to_getRemoteCharacters_when_there_are_not_local_characters(){
        runBlocking {
            val mutableListData = MutableLiveData(fakeRepository.getRemoteComments(ID_POST))
            BDDMockito.given(commentRepository.getLocalComments(ID_POST)).willReturn(listOf())
            BDDMockito.given(commentRemoteSource.getAllComments(ID_POST)).willReturn(mutableListData)
            commentRepository.getAllComments(ID_POST)
            BDDMockito.verify(commentRemoteSource).getAllComments(ID_POST)
        }
    }

    @Test
    fun should_not_calls_to_getRemoteCharacters_when_there_are_local_characters(){
        runBlocking {
            BDDMockito.given(commentRepository.getLocalComments(ID_POST)).willReturn(listOf(fakeRepository.comment1))
            commentRepository.getAllComments(ID_POST)
            BDDMockito.verify(commentRemoteSource, BDDMockito.never()).getAllComments(ID_POST)
        }
    }
}