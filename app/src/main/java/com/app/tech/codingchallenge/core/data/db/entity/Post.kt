package com.app.tech.codingchallenge.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.tech.codingchallenge.core.data.db.AppDatabase

@Entity (tableName = AppDatabase.POSTS)
data class Post(
    @PrimaryKey
    val id: Int,
    val body: String,
    val title: String,
    val userId: Int
)