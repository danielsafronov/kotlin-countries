package app.countries.data.mapper

import app.countries.base.Mapper
import app.countries.data.model.Country
import app.countries.network.model.CountryNetworkDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryNetworkDtoToCountyMapper @Inject constructor(): Mapper<CountryNetworkDto, Country> {
    override suspend fun map(from: CountryNetworkDto): Country =
        Country(
            name = from.name,
            alpha3Code = from.alpha3Code,
            region = from.region,
            subregion = from.subregion,
            population = from.population,
            latitude = from.latlng?.getOrNull(0),
            longitude = from.latlng?.getOrNull(1),
        )
}
