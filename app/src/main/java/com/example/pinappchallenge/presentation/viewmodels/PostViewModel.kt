package com.example.pinappchallenge.presentation.viewmodels

import androidx.lifecycle.*
import com.example.pinappchallenge.data.models.DataPosts
import com.example.pinappchallenge.domain.usecase.GetAllPostsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class PostViewModel(private val getAllPostsUseCase: GetAllPostsUseCase): ViewModel() {

    private val listData: MutableLiveData<DataPosts> by lazy {
        MutableLiveData<DataPosts>().also {
            getPosts()
        }
    }

    fun setListData(result: DataPosts) {
        listData.postValue(result)
    }

    private fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            setListData(getAllPostsUseCase.call())
        }
    }

    fun getPostsLiveData(): LiveData<DataPosts> {
        return listData
    }

    class PostViewModelFactory(private val getAllPostsUseCase: GetAllPostsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllPostsUseCase::class.java).newInstance(getAllPostsUseCase)
        }
    }
}