package app.countries.base

/**
 * Result wrapper.
 */
sealed class Result<out A : Any?> {
    data class Success<out A : Any?>(val value: A) : Result<A>()
    data class Error(val error: Throwable) : Result<Nothing>()

    /**
     * Returns [Result] value instance or throws error.
     * @return instance of [A].
     */
    fun getOrThrow(): A = when (this) {
        is Success -> value
        is Error -> throw error
    }
}
