package app.countries.network

import app.countries.base.Result
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
     * Returns a [Flow] with [List] of [Country] wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    fun all(): Flow<Result<List<Country>>>
}

/**
 * Country SDK.
 */
internal class CountrySDK @Inject constructor(
    private val countryService: CountryService,
) : CountrySDKInterface {
    /**
     * Returns a [Flow] with [List] of [Country] wrapped into [Result] instance.
     * @return instance of [Flow].
     */
    override fun all(): Flow<Result<List<Country>>> =
        flow {
            try {
                val countries = countryService.all()
                emit(Result.Success(countries))
            } catch (t: Throwable) {
                emit(Result.Error(t))
            }
        }
}