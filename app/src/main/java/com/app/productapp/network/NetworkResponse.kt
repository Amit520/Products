package com.app.productapp.network

sealed class NetworkResponse<T>(val data: T? = null, val message: String? = "") {
    class Success<T>(data: T? = null) : NetworkResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResponse<T>(data, message)
    class Loading<T> : NetworkResponse<T>()
}