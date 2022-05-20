package com.app.tech.codingchallenge.core.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.app.tech.codingchallenge.core.data.db.AppDatabase.Companion.POSTS
import com.app.tech.codingchallenge.core.data.db.entity.Post

@Dao
interface PostDao : BaseDao<Post> {

    @Query("SELECT * FROM $POSTS")
    fun getPosts(): List<Post>

}