package com.example.pinappchallenge.core

import android.app.Application
import com.example.pinappchallenge.di.component.ApplicationComponent
import com.example.pinappchallenge.di.component.DaggerApplicationComponent
import com.example.pinappchallenge.di.module.ApplicationModule

/**
 * @author Axel Sanchez
 */
class MyApplication: Application() {
    val component: ApplicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
}