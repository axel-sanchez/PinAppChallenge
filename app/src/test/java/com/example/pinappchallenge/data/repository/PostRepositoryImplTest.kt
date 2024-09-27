package com.example.pinappchallenge.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.pinappchallenge.data.source.PostLocalSource
import com.example.pinappchallenge.data.source.PostRemoteSource
import com.example.pinappchallenge.domain.repository.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.BDDMockito

class PostRepositoryImplTest{
    private val fakeRepository = FakePostRepository()

    private val postRemoteSource: PostRemoteSource =
        BDDMockito.mock(PostRemoteSource::class.java)
    private val postLocalSource: PostLocalSource =
        BDDMockito.mock(PostLocalSource::class.java)
    private val postRepository: PostRepository = PostRepositoryImpl(postRemoteSource, postLocalSource)

    @Test
    fun should_calls_to_getRemotePosts_when_there_are_not_local_posts(){
        runBlocking {
            val mutableListData = MutableLiveData(fakeRepository.getRemotePosts())
            BDDMockito.given(postRepository.getLocalPosts()).willReturn(listOf())
            BDDMockito.given(postRemoteSource.getAllPosts()).willReturn(mutableListData)
            postRepository.getAllPosts()
            BDDMockito.verify(postRemoteSource).getAllPosts()
        }
    }

    @Test
    fun should_not_calls_to_getRemoteCharacters_when_there_are_local_characters(){
        runBlocking {
            BDDMockito.given(postRepository.getLocalPosts()).willReturn(listOf(fakeRepository.post1))
            postRepository.getAllPosts()
            BDDMockito.verify(postRemoteSource, BDDMockito.never()).getAllPosts()
        }
    }
}