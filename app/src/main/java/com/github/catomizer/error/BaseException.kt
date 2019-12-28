package com.github.catomizer.error

open class BaseException : RuntimeException {
    constructor() : super()
    constructor(cause: Throwable) : super(cause)
}
