package com.example.pinappchallenge.data.room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.models.Post

/**
 * @author Axel Sanchez
 */
@Database(
    entities = [Post::class, Comment::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun postDao(): PostDao
}