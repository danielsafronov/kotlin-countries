package app.countries.data.repository

import app.countries.base.Result
import app.countries.base.map
import app.countries.data.mapper.CountryNetworkDtoToCountyMapper
import app.countries.data.model.Country
import app.countries.network.CountrySDKInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Remote country repository interface.
 */
internal interface RemoteCountryRepositoryInterface {
    /**
     * Returns a [Flow] with [List] of [Country] wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    fun observe(): Flow<Result<List<Country>>>
}

/**
 * Remote country repository.
 */
internal class RemoteCountryRepository @Inject constructor(
    private val sdk: CountrySDKInterface,
    private val countryNetworkDtoMapper: CountryNetworkDtoToCountyMapper,
) : RemoteCountryRepositoryInterface {
    /**
     * Returns a [Flow] with [List] of [Country] wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    override fun observe(): Flow<Result<List<Country>>> =
        sdk.all()
            .map { result ->
                return@map when (result) {
                    is Result.Success -> Result.Success(countryNetworkDtoMapper.map(result.value))
                    is Result.Error -> Result.Error(result.error)
                }
            }
            .catch { t ->
                emit(Result.Error(t))
            }
}
