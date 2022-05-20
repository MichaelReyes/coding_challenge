package com.app.tech.codingchallenge.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.tech.codingchallenge.core.data.db.AppDatabase.Companion.COMMENTS

@Entity(tableName = COMMENTS)
data class Comment(
    @PrimaryKey
    val id: Int,
    val body: String,
    val email: String,
    val name: String,
    val postId: Int
)