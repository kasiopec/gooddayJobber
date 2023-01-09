package com.gooddayjobber.network

sealed class NetworkResponse<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResponse<T>()
    class Error<T : Any>(val code: Int, val message: String) : NetworkResponse<T>()
    class Exception<T: Any>(val ex: Throwable): NetworkResponse<T>()
}
