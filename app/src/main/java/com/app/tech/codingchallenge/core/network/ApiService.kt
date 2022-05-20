package com.app.tech.codingchallenge.core.network


import com.app.tech.codingchallenge.core.data.db.entity.Comment
import com.app.tech.codingchallenge.core.data.db.entity.Post
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{postId}/comments")
    suspend fun getPostComments(@Path("postId") postId: Int): List<Comment>

}