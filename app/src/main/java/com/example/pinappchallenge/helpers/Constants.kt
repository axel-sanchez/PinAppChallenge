package com.example.pinappchallenge.helpers

object Constants {
    const val ID_POST = "idPost"
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    const val GET_POSTS = "posts"
    const val GET_COMMENTS = "comments"

    enum class ApiError(var error: String) {
        GENERIC("Hubo un error al obtener los posts"),
        GENERIC_DETAILS("Hubo un error al obtener los comentarios del post"),
        EMPTY_CHARACTERS("No se obtuvo ningún post"),
        NETWORK_ERROR("Hubo un error en la conexión de internet")
    }
}