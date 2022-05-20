package com.app.tech.codingchallenge.feature.dashboard.post_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.tech.codingchallenge.core.base.BaseViewModel
import com.app.tech.codingchallenge.core.data.db.entity.Comment
import com.app.tech.codingchallenge.core.data.db.entity.Post
import com.app.tech.codingchallenge.core.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : BaseViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    private val _postComments = MutableLiveData<List<Comment>>()
    val postComments: LiveData<List<Comment>> = _postComments

    fun setPost(post: Post) {
        _post.value = post

        apiRepository.getPostComments(post.id)
            .onEach {
                handleResponse(it) {
                    _postComments.value = it
                }
            }.launchIn(viewModelScope)
    }

}