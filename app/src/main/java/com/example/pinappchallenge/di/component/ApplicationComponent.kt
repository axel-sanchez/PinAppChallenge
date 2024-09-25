package com.example.pinappchallenge.di.component

import com.example.pinappchallenge.di.module.ApplicationModule
import com.example.pinappchallenge.presentation.ui.PostsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(postsFragment: PostsFragment)
    //fun inject(detailsFragment: DetailsFragment)
}