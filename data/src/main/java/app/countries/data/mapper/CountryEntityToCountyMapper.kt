package app.countries.data.mapper

import app.countries.base.Mapper
import app.countries.data.model.Country
import app.countries.storage.entities.CountryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryEntityToCountyMapper @Inject constructor(): Mapper<CountryEntity, Country> {
    override suspend fun map(from: CountryEntity): Country =
        Country(
            id = from.id,
            name = from.name,
            alpha3Code = from.alpha3Code,
            region = from.region,
            subregion = from.subregion,
            population = from.population,
            latitude = from.latitude,
            longitude = from.longitude,
        )
}
