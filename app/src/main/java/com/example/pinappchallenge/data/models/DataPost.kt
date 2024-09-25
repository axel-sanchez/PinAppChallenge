package com.example.pinappchallenge.data.models

import com.example.pinappchallenge.helpers.Constants

/**
 * @author Axel Sanchez
 */
data class DataPost(
    val results: List<Post?>? = null,
    var apiError: Constants.ApiError? = null
)