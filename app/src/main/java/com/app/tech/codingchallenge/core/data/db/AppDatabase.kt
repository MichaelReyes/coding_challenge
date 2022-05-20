package com.app.tech.codingchallenge.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.tech.codingchallenge.core.data.db.dao.CommentDao
import com.app.tech.codingchallenge.core.data.db.dao.PostDao
import com.app.tech.codingchallenge.core.data.db.entity.Comment
import com.app.tech.codingchallenge.core.data.db.entity.Post

@Database(
    entities = [Post::class, Comment::class],
    version = AppDatabase.VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "codechallenge.db"
        const val VERSION = 1
        const val POSTS = "TBL_POST"
        const val COMMENTS = "TBL_COMMENT"
    }

    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}