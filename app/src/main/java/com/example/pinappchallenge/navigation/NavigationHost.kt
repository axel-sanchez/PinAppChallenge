package com.example.pinappchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pinappchallenge.navigation.Destinations.*
import com.example.pinappchallenge.helpers.Constants.ID_POST
import com.example.pinappchallenge.presentation.ui.compose.CommentsScreen
import com.example.pinappchallenge.presentation.ui.compose.PostsScreen
import com.example.pinappchallenge.presentation.viewmodels.CommentViewModel
import com.example.pinappchallenge.presentation.viewmodels.PostViewModel

/**
 * @author Axel Sanchez
 */

@Composable
fun NavigationHost(postViewModel: PostViewModel, commentsViewModel: CommentViewModel) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = PostsScreen.route){
        composable(PostsScreen.route){
            PostsScreen(
                navigateCommentsScreen = { idPost ->
                    navController.navigate(CommentsScreen.createRoute(idPost))
                },
                viewModel = postViewModel
            )
        }

        composable(CommentsScreen.route){ navBackStackEntry ->
            val idPost = navBackStackEntry.arguments?.getInt(ID_POST)?:0
            CommentsScreen(
                idPost,
                commentsViewModel)
        }
    }
}