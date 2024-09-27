package com.example.pinappchallenge.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.example.pinappchallenge.core.MyApplication
import com.example.pinappchallenge.domain.usecase.GetAllCommentsUseCase
import com.example.pinappchallenge.domain.usecase.GetAllPostsUseCase
import com.example.pinappchallenge.navigation.NavigationHost
import com.example.pinappchallenge.presentation.ui.theme.PinAppTheme
import com.example.pinappchallenge.presentation.viewmodels.CommentViewModel
import com.example.pinappchallenge.presentation.viewmodels.PostViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getAllPostsUseCase: GetAllPostsUseCase

    @Inject
    lateinit var getAllCommentsUseCase: GetAllCommentsUseCase

    private val postViewModel: PostViewModel by viewModels(
        factoryProducer = { PostViewModel.PostViewModelFactory(getAllPostsUseCase) }
    )

    private val commentViewModel: CommentViewModel by viewModels(
        factoryProducer = { CommentViewModel.CommentViewModelFactory(getAllCommentsUseCase) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MyApplication).component.inject(this)
        setContent {
            PinAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background){
                    NavigationHost(postViewModel, commentViewModel)
                }
            }
        }
    }
}