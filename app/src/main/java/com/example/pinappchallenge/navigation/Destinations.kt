package com.example.pinappchallenge.navigation

import com.example.pinappchallenge.helpers.Constants.ID_POST

/**
 * @author Axel Sanchez
 */
sealed class Destinations(
    var route: String
){
    object PostsScreen: Destinations("postsScreen")
    object CommentsScreen: Destinations("commentsScreen/{${ID_POST}}"){
        fun createRoute(idPost: Int) = "commentsScreen/$idPost"
    }
}
