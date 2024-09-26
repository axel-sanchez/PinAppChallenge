package com.example.pinappchallenge.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pinappchallenge.data.models.DataComments
import com.example.pinappchallenge.data.service.ApiServicePost
import com.example.pinappchallenge.helpers.Constants
import com.example.pinappchallenge.helpers.NetworkHelper
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface CommentRemoteSource {
    suspend fun getAllComments(idPost: Int): MutableLiveData<DataComments>
}

@Singleton
class CommentRemoteSourceImpl @Inject constructor(private val service: ApiServicePost,
                                               private val networkHelper: NetworkHelper
): CommentRemoteSource{

    override suspend fun getAllComments(idPost: Int): MutableLiveData<DataComments> {
        val mutableLiveData = MutableLiveData<DataComments>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = DataComments(apiError = Constants.ApiError.NETWORK_ERROR)
                return mutableLiveData
            }

            val response = service.getComments()
            if (response.isSuccessful) {
                Log.i("Successful Response", response.toString())

                response.body()?.let { result ->
                    mutableLiveData.value = DataComments(results = result)
                } ?: kotlin.run {
                    mutableLiveData.value = DataComments(apiError = Constants.ApiError.GENERIC)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = Constants.ApiError.GENERIC
                apiError.error = response.message()
                mutableLiveData.value = DataComments(apiError = apiError)
            }
        } catch (e: IOException) {
            mutableLiveData.value = DataComments(apiError = Constants.ApiError.GENERIC)
            Log.e(
                "PostRemoteSourceImpl",
                e.message?:"Error al obtener los comments"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }
}