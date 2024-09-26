package com.example.pinappchallenge.data.room

import androidx.room.TypeConverter
import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.models.Post
import com.google.gson.Gson

private const val nullStr = "null"
/**
 * @author Axel Sanchez
 */
class Converters{
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromPost(post: Post?): String? {
        post?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toPost(post: String?): Post? {
        post?.let {
            return gson.fromJson(it, Post::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromComment(comment: Comment?): String? {
        comment?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toComment(comment: String?): Comment? {
        comment?.let {
            return gson.fromJson(it, Comment::class.java)
        } ?: return null
    }
}