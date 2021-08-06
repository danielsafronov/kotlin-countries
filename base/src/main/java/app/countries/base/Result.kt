package app.countries.base

/**
 * Result wrapper.
 */
sealed class Result<out A : Any> {
    data class Success<out A : Any>(val value: A) : Result<A>()
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

/**
 * Returns a [value] wrapped into success [Result] instance.
 * @param [value] success result value.
 * @return instance of [Result].
 */
fun <A : Any> Success(value: A): Result<A> =
    Result.Success(value)

/**
 * Returns a [Unit] wrapped into success [Result] instance.
 * @return instance of [Result].
 */
fun Success(): Result<Unit> =
    Result.Success(Unit)

/**
 * Returns a [error] wrapped into error [Result] instance.
 * @param [error] error result value.
 * @return instance of [Result].
 */
fun Error(error: Throwable): Result<Nothing> =
    Result.Error(error)
