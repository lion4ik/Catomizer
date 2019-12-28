package com.github.catomizer.error

class NoInternetException : BaseException {
    constructor() : super()
    constructor(cause: Throwable) : super(cause)
}
