package com.app.tech.codingchallenge.feature.dashboard.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.tech.codingchallenge.core.base.BaseViewModel
import com.app.tech.codingchallenge.core.data.db.entity.Post
import com.app.tech.codingchallenge.core.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : BaseViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _searchTextQuery = MutableLiveData<String>()
    val searchTextQuery: LiveData<String> = _searchTextQuery

    private val searchStreamFlow = MutableSharedFlow<String>()

    init {
        fetchPosts()

        viewModelScope.launch {
            observeDataStream()
        }
    }

    fun fetchPosts() {
        apiRepository.getPosts()
            .onEach {
                handleResponse(it) {
                    _posts.postValue(it)
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun observeDataStream() {
        searchStreamFlow.debounce(500)
            .collect { searchQuery ->
                _searchTextQuery.postValue(searchQuery)
            }
    }

    fun onDataChange(text: CharSequence) {
        viewModelScope.launch {
            searchStreamFlow.emit(text.toString())
        }
    }
}