package app.countries.network.service

import app.countries.network.model.Country
import retrofit2.http.GET

internal interface CountryService {
    @GET("v2/all")
    suspend fun all(): List<Country>
}
