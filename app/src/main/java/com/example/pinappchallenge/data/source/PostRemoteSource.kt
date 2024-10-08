package com.example.pinappchallenge.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pinappchallenge.data.models.DataPosts
import com.example.pinappchallenge.data.service.ApiServicePost
import com.example.pinappchallenge.helpers.Constants
import com.example.pinappchallenge.helpers.NetworkHelper
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface PostRemoteSource {
    suspend fun getAllPosts(): MutableLiveData<DataPosts>
}

@Singleton
class PostRemoteSourceImpl @Inject constructor(private val service: ApiServicePost,
                                               private val networkHelper: NetworkHelper
): PostRemoteSource{
    override suspend fun getAllPosts(): MutableLiveData<DataPosts> {
        val mutableLiveData = MutableLiveData<DataPosts>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = DataPosts(apiError = Constants.ApiError.NETWORK_ERROR)
                return mutableLiveData
            }

            val response = service.getPosts()
            if (response.isSuccessful) {
                Log.i("Successful Response", response.toString())

                response.body()?.let { result ->
                    mutableLiveData.value = DataPosts(results = result)
                } ?: kotlin.run {
                    mutableLiveData.value = DataPosts(apiError = Constants.ApiError.GENERIC)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = Constants.ApiError.GENERIC
                apiError.error = response.message()
                mutableLiveData.value = DataPosts(apiError = apiError)
            }
        } catch (e: IOException) {
            mutableLiveData.value = DataPosts(apiError = Constants.ApiError.GENERIC)
            Log.e(
                "PostRemoteSourceImpl",
                e.message?:"Error al obtener los posts"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }
}