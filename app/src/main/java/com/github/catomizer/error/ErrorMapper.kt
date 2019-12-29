package com.github.catomizer.error

interface ErrorMapper {

    fun mapError(error: Throwable): BaseException
}