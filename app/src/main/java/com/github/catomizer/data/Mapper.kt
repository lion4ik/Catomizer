package com.github.catomizer.data

interface Mapper<In,Out> {

    fun mapTo(obj: In): Out

    fun mapFrom(obj: Out): In
}