package com.example.pinappchallenge.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey var id: Int? = null,
    var title: String? = null,
    var body: String? = null,
    var userId: Int? = null
)