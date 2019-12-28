package com.github.catomizer.error

class ApiError : BaseException {
    constructor() : super()
    constructor(cause: Throwable) : super(cause)
}
