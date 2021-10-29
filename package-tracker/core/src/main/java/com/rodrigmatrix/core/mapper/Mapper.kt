package com.rodrigmatrix.core.mapper

interface Mapper<S, R> {
    fun map(source: S): R
}