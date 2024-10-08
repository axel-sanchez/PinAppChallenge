package com.example.pinappchallenge.data.models

import com.example.pinappchallenge.helpers.Constants

/**
 * @author Axel Sanchez
 */
class DataComments(
    val results: List<Comment?>? = null,
    var apiError: Constants.ApiError? = null
)