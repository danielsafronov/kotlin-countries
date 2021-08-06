package app.countries.base

interface Mapper<F, T> {
    suspend fun map(from: F): T
}

suspend fun <F, T> Mapper<F, T>.map(from: List<F>): List<T> =
    from.map { map(it) }
