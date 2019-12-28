package com.github.catomizer.error

interface ErrorHandler {

    fun handleError(error: Throwable)
}