package com.example.pinappchallenge.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
    @PrimaryKey var id: Int? = null,
    var name: String? = null,
    var body: String? = null,
    var email: String? = null,
    var postId: Int? = null
)