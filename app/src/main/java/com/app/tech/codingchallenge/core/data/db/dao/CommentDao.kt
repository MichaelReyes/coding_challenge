package com.app.tech.codingchallenge.core.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.app.tech.codingchallenge.core.data.db.AppDatabase.Companion.COMMENTS
import com.app.tech.codingchallenge.core.data.db.entity.Comment

@Dao
interface CommentDao : BaseDao<Comment> {

    @Query("SELECT * FROM $COMMENTS WHERE postId=:postId")
    fun getPostComments(postId: Int): List<Comment>

}