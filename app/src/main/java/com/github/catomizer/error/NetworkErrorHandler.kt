package com.github.catomizer.error

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorHandler : ErrorHandler {

    override fun handleError(error: Throwable) =
        when (error) {
            is HttpException -> throw ApiError(error)
            is SocketTimeoutException -> throw NoInternetException(error)
            is UnknownHostException -> throw NoInternetException(error)
            is IOException -> throw NoInternetException(error)
            else -> throw BaseException(error)
        }
}