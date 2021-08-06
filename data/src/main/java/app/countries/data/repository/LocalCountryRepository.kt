package app.countries.data.repository

import app.countries.base.Result
import app.countries.base.map
import app.countries.data.mapper.CountryEntityToCountyMapper
import app.countries.data.mapper.CountryToCountyEntityMapper
import app.countries.data.model.Country
import app.countries.storage.dao.CountryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Local country repository interface.
 */
internal interface LocalCountryRepositoryInterface {
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

    /**
     * Store [Country] instance in data storage.
     * @param [country] instance of [Country].
     */
    suspend fun store(country: Country)
}

/**
 * Local country repository.
 */
internal class LocalCountryRepository @Inject constructor(
    private val dao: CountryDao,
    private val countryMapper: CountryToCountyEntityMapper,
    private val countryEntityMapper: CountryEntityToCountyMapper,
) : LocalCountryRepositoryInterface {
    /**
     * Returns a [Flow] with [List] of [Country] wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    override fun observe(): Flow<Result<List<Country>>> =
        dao.observe()
            .map { Result.Success(countryEntityMapper.map(it)) }
            .catch { t -> Result.Error(t) }

    /**
     * Returns a [Flow] with [Country] instance wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    override fun observe(id: String): Flow<Result<Country?>> =
        dao.observe(id)
            .map { Result.Success(it?.let { countryEntityMapper.map(it) }) }
            .catch { t -> Result.Error(t) }

    /**
     * Store [Country] instance in data storage.
     * @param [country] instance of [Country].
     */
    override suspend fun store(country: Country) =
        dao.store(countryMapper.map(country))
}
