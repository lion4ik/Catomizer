package com.github.catomizer.error

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorMapper : ErrorMapper {

    override fun mapError(error: Throwable): BaseException =
        when (error) {
            is HttpException -> ApiError(error)
            is SocketTimeoutException -> NoInternetException(error)
            is UnknownHostException -> NoInternetException(error)
            is IOException -> NoInternetException(error)
            else -> BaseException(error)
        }
}