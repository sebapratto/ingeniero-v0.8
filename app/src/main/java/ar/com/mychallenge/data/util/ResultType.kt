package ar.com.mychallenge.data.util

sealed class ResultType<out T> {
    data class Success<out T>(val data: T) : ResultType<T>()
    data class Error(val exception: Exception? = null, val message: String = "An unexpected error occurred") : ResultType<Nothing>()
    object Loading : ResultType<Nothing>()
}