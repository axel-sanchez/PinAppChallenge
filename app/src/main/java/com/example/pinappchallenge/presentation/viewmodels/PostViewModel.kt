package com.example.pinappchallenge.presentation.viewmodels

import androidx.lifecycle.*
import com.example.pinappchallenge.data.models.DataPost
import com.example.pinappchallenge.domain.usecase.GetAllPostsUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class PostViewModel(private val getAllPostsUseCase: GetAllPostsUseCase): ViewModel() {

    private val listData: MutableLiveData<DataPost> =
        MutableLiveData<DataPost>()


    fun setListData(result: DataPost) {
        listData.postValue(result)
    }

    fun getPosts() {
        viewModelScope.launch {
            setListData(getAllPostsUseCase.call())
        }
    }

    fun getPostsLiveData(): LiveData<DataPost> {
        return listData
    }

    class PostViewModelFactory(private val getAllPostsUseCase: GetAllPostsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllPostsUseCase::class.java).newInstance(getAllPostsUseCase)
        }
    }
}