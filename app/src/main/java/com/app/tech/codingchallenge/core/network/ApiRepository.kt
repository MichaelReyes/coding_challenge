package com.app.tech.codingchallenge.core.network

import com.app.tech.codingchallenge.core.data.db.dao.CommentDao
import com.app.tech.codingchallenge.core.data.db.dao.PostDao
import com.app.tech.codingchallenge.core.data.db.entity.Comment
import com.app.tech.codingchallenge.core.data.db.entity.Post
import com.app.tech.codingchallenge.core.data.network.Resource
import com.app.tech.codingchallenge.core.extension.safeCall
import com.app.tech.codingchallenge.core.utils.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface ApiRepository {

    fun getPosts(): Flow<Resource<List<Post>>>
    fun getPostComments(postId: Int): Flow<Resource<List<Comment>>>

    class ApiRepositoryImpl
    @Inject constructor(
        private val service: ApiService,
        private val postDao: PostDao,
        private val commentDao: CommentDao,
        private val networkHandler: NetworkHandler
    ) : ApiRepository {
        override fun getPosts(): Flow<Resource<List<Post>>> {
            return flow {
                this.safeCall<List<Post>> {
                    if (networkHandler.isConnected) {
                        val response = service.getPosts()
                        postDao.insert(response)

                        emit(Resource.success(data = response))
                    } else {
                        emit(Resource.success(data = postDao.getPosts()))
                    }
                }
            }.flowOn(Dispatchers.IO)
        }

        override fun getPostComments(postId: Int): Flow<Resource<List<Comment>>> {
            return flow {
                this.safeCall<List<Comment>> {
                    if (networkHandler.isConnected) {
                        val response = service.getPostComments(postId)
                        commentDao.insert(response)

                        emit(Resource.success(data = response))
                    } else {
                        emit(Resource.success(data = commentDao.getPostComments(postId)))
                    }
                }
            }.flowOn(Dispatchers.IO)
        }
    }
}