package com.luisfagundes.h2o.core.common.result

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    data object Empty : Result<Nothing>
}