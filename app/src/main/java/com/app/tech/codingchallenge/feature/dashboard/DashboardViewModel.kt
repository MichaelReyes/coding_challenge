package com.app.tech.codingchallenge.feature.dashboard

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
class DashboardViewModel @Inject constructor() : BaseViewModel() {

}