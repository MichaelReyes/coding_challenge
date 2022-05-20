package com.app.tech.codingchallenge.core.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
 * To act as a super class for all other ViewModel.
 * Handing common LiveDatas used in ViewModel classes such as
 * Loading, error, internet connection, message, etc.
 */
abstract class BaseViewModel : ViewModel() {

    //for navigation with arguments, added bundle
    private val _navigationId = MutableLiveData<Pair<Int, Bundle?>>()
    val navigationId: LiveData<Pair<Int, Bundle?>> = _navigationId

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun setNavigationId(value: Pair<Int, Bundle?>) {
        _navigationId.postValue(value)
    }

    fun setLoading(value: Boolean) {
        _loading.postValue(value)
    }

    fun setError(value: String) {
        _error.postValue(value)
    }

    protected fun handleException(exception: Throwable, errorMessage: (String?) -> Unit) {
        if (exception is HttpException) {
            errorMessage.invoke(exception.response()?.errorBody()?.string())
        } else
            errorMessage.invoke(exception.localizedMessage)
    }

    override fun onCleared() {
        super.onCleared()
    }
}