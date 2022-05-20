package com.app.tech.codingchallenge.feature.dashboard.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.tech.codingchallenge.core.base.BaseViewModel
import com.app.tech.codingchallenge.core.data.db.entity.Post
import com.app.tech.codingchallenge.core.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : BaseViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        apiRepository.getPosts()
            .onEach {
                handleResponse(it) {
                    _posts.postValue(it)
                }
            }.launchIn(viewModelScope)
    }
}