package com.github.catomizer.error

import androidx.annotation.StringRes
import com.github.catomizer.R

class ErrorHandler {

    fun proceed(error: Throwable, messageListener: (Int) -> Unit = {}) {
        messageListener(toUserMessage(error))
    }

    @StringRes
    private fun toUserMessage(error: Throwable): Int =
        when (error) {
            is ApiError -> R.string.error_service_unavailable
            is NoInternetException -> R.string.error_network
            else -> R.string.error_unknown
        }
}