package app.countries.data.repository

import app.countries.base.map
import app.countries.data.mapper.CountryEntityToCountyMapper
import app.countries.data.mapper.CountryNetworkDtoToCountyMapper
import app.countries.data.mapper.CountryToCountyEntityMapper
import app.countries.data.model.Country
import app.countries.network.CountrySDKInterface
import app.countries.storage.dao.CountryDao
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Local country repository interface.
 */
internal interface LocalCountryRepositoryInterface {
    /**
     * Observe country list.
     * @return [Flow] of [Country] list.
     */
    fun observe(): Flow<List<Country>>

    /**
     * Observe county by identifier.
     * @param [id] [String] country identifier.
     * @return [Flow] with [Country] instance or null.
     */
    fun observe(id: String): Flow<Country?>

    /**
     * Store [Country] instance in data storage.
     * @param [country] instance of [Country].
     */
    suspend fun store(country: Country)
}

/**
 * Remote country repository interface.
 */
internal interface RemoteCountryRepositoryInterface {
    /**
     * Observe country list.
     * @return [Flow] of [Country] list.
     */
    fun observe(): Flow<List<Country>>
}

/**
 * Country repository interface.
 */
interface CountryRepositoryInterface {
    /**
     * Observe country list.
     * @return [Flow] of [Country] list.
     */
    fun observe(): Flow<List<Country>>

    /**
     * Observe county by identifier.
     * @param [id] [String] country identifier.
     * @return [Flow] with [Country] instance or null.
     */
    fun observe(id: String): Flow<Country?>
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
     * Observe country list.
     * @return [Flow] of [Country] list.
     */
    override fun observe(): Flow<List<Country>> =
        dao.observe()
            .map { countryEntityMapper.map(it) }

    /**
     * Observe county by identifier.
     * @param [id] [String] country identifier.
     * @return [Flow] with [Country] instance or null.
     */
    override fun observe(id: String): Flow<Country?> =
        dao.observe(id)
            .map { it?.let { countryEntityMapper.map(it) } }

    /**
     * Store [Country] instance in data storage.
     * @param [country] instance of [Country].
     */
    override suspend fun store(country: Country) =
        dao.store(countryMapper.map(country))
}

/**
 * Remote country repository.
 */
internal class RemoteCountryRepository @Inject constructor(
    private val sdk: CountrySDKInterface,
    private val countryNetworkDtoMapper: CountryNetworkDtoToCountyMapper,
) : RemoteCountryRepositoryInterface {
    /**
     * Observe country list.
     * @return [Flow] of [Country] list.
     */
    override fun observe(): Flow<List<Country>> =
        sdk.all().map { countries ->
            countryNetworkDtoMapper.map(countries)
        }
}

/**
 * Country repository.
 */
internal class CountryRepository(
    private val localRepository: LocalCountryRepositoryInterface,
    private val remoteRepository: RemoteCountryRepositoryInterface,
) : CountryRepositoryInterface {
    /**
     * Observe country list.
     * @return [Flow] of [Country] list.
     */
    @OptIn(FlowPreview::class)
    override fun observe(): Flow<List<Country>> =
        remoteRepository
            .observe()
            .onEach { countries ->
                countries.forEach { country ->
                    localRepository.store(country)
                }
            }
            .flatMapMerge { localRepository.observe() }

    /**
     * Observe county by identifier.
     * @param [id] [String] country identifier.
     * @return [Flow] with [Country] instance or null.
     */
    override fun observe(id: String): Flow<Country?> =
        localRepository.observe(id)
}
