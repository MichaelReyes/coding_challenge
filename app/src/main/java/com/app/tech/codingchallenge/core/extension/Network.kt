package com.app.tech.codingchallenge.core.extension

import com.app.tech.codingchallenge.BuildConfig
import com.app.tech.codingchallenge.core.data.network.Resource
import com.app.tech.codingchallenge.core.data.network.Status
import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> FlowCollector<Resource<T>>.safeCall(
    errorHandlingCall: (suspend () -> Unit)? = null, //In case there's an extra handling when receiving error on call
    call: suspend () -> Unit
): FlowCollector<Resource<T>> {
    return this.apply {
        emit(Resource.loading(data = null))
        try {
            call.invoke()
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }

            errorHandlingCall?.invoke() ?: kotlin.run {
                emit(
                    Resource.error(
                        data = null,
                        message = e.message ?: "Error Occurred!"
                    )
                )
            }

        }
    }
}

suspend fun <T> Resource<T>.handleResponse(
    onError: (suspend (Resource<T>) -> Unit)? = null,
    onLoading: (suspend (Resource<T>) -> Unit)? = null,
    onSuccess: suspend (Resource<T>) -> Unit,
) {
    when (status) {
        Status.SUCCESS -> {
            onSuccess.invoke(this)
        }
        Status.ERROR -> {
            onError?.invoke(this)
        }
        Status.LOADING -> {
            onLoading?.invoke(this)
        }
    }
}