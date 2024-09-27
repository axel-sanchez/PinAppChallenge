package com.example.pinappchallenge.di.component

import com.example.pinappchallenge.di.module.ApplicationModule
import com.example.pinappchallenge.navigation.Destinations.*
import com.example.pinappchallenge.presentation.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(postsScreen: PostsScreen)
    fun inject(commentsScreen: CommentsScreen)
    fun inject(mainActivity: MainActivity)
}