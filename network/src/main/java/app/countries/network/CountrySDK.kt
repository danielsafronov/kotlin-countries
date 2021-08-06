package app.countries.network

import app.countries.network.model.Country
import app.countries.network.service.CountryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Country SDK interface.
 */
interface CountrySDKInterface {
    /**
     * Returns all countries.
     */
    fun all(): Flow<List<Country>>
}

/**
 * Country SDK.
 */
internal class CountrySDK @Inject constructor(
    private val countryService: CountryService,
): CountrySDKInterface {
    /**
     * Returns all countries.
     */
    override fun all(): Flow<List<Country>> =
        flow {
            val countries = countryService.all()
            emit(countries)
        }
}