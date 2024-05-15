package data.remote

sealed class Resources<out T> {
    data class SUCCESS<T>(val response: T) : Resources<T>()
    data class ERROR(val message: String): Resources<Nothing>()
    object LOADING: Resources<Nothing>()
}