package com.example.pinappchallenge.presentation.viewmodels

import androidx.lifecycle.*
import com.example.pinappchallenge.data.models.DataComments
import com.example.pinappchallenge.domain.usecase.GetAllCommentsUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class CommentViewModel(private val getAllCommentsUseCase: GetAllCommentsUseCase): ViewModel() {

    private var listData: MutableLiveData<DataComments> = MutableLiveData<DataComments>()

    fun setListData(result: DataComments) {
        listData.postValue(result)
    }

    fun getComments(idPost: Int) {
        viewModelScope.launch {
            setListData(getAllCommentsUseCase.call(idPost))
        }
    }

    fun getCommentsLiveData(): LiveData<DataComments> {
        return listData
    }

    fun reset(){
        listData = MutableLiveData<DataComments>()
    }

    class CommentViewModelFactory(private val getAllCommentsUseCase: GetAllCommentsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllCommentsUseCase::class.java).newInstance(getAllCommentsUseCase)
        }
    }
}