package com.app.tech.codingchallenge.core.network


import com.app.tech.codingchallenge.core.data.db.entity.Post
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

}