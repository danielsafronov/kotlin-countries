package app.countries.data.repository

import app.countries.base.Result
import app.countries.data.model.Country
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach

/**
 * Country repository interface.
 */
interface CountryRepositoryInterface {
    /**
     * Returns a [Flow] with [List] of [Country] wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    fun observe(): Flow<Result<List<Country>>>

    /**
     * Returns a [Flow] with [Country] instance wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    fun observe(id: String): Flow<Result<Country?>>
}

/**
 * Country repository.
 */
internal class CountryRepository(
    private val localRepository: LocalCountryRepositoryInterface,
    private val remoteRepository: RemoteCountryRepositoryInterface,
) : CountryRepositoryInterface {
    /**
     * Returns a [Flow] with [List] of [Country] wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    @OptIn(FlowPreview::class)
    override fun observe(): Flow<Result<List<Country>>> =
        remoteRepository
            .observe()
            .onEach { result ->
                if (result is Result.Success) {
                    result.value.forEach { country ->
                        localRepository.store(country)
                    }
                }
            }
            .flatMapMerge { localRepository.observe() }

    /**
     * Observe county by identifier.
     * @param [id] [String] country identifier.
     * @return [Flow] with [Country] instance or null.
     */
    override fun observe(id: String): Flow<Result<Country?>> =
        localRepository.observe(id)
}
