package ar.com.mychallenge.data.util

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val message: String = "An unexpected error occurred") : Result<Nothing>()
    object Loading : Result<Nothing>()
}