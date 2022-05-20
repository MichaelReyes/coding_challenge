package com.app.tech.codingchallenge.core.network

import com.app.tech.codingchallenge.core.data.db.dao.PostDao
import com.app.tech.codingchallenge.core.data.db.entity.Post
import com.app.tech.codingchallenge.core.data.network.Resource
import com.app.tech.codingchallenge.core.extension.safeCall
import com.app.tech.codingchallenge.core.utils.NetworkHandler
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface ApiRepository {

    fun getPosts(): Flow<Resource<List<Post>>>

    class ApiRepositoryImpl
    @Inject constructor(
        private val service: ApiService,
        private val postDao: PostDao,
        private val networkHandler: NetworkHandler
    ) : ApiRepository {
        override fun getPosts(): Flow<Resource<List<Post>>> {
            return flow {
                this.safeCall<List<Post>> {
                    if (networkHandler.isConnected) {
                        val response = service.getPosts()
                        postDao.insert(response)

                        emit(Resource.success(data = service.getPosts()))
                    } else {
                        emit(Resource.success(data = postDao.getPosts()))
                    }
                }
            }
        }
    }
}