package app.countries.data.mapper

import app.countries.base.Mapper
import app.countries.data.model.Country
import app.countries.storage.entities.CountryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryToCountyEntityMapper @Inject constructor(): Mapper<Country, CountryEntity> {
    override suspend fun map(from: Country): CountryEntity =
        CountryEntity(
            name = from.name,
            alpha3Code = from.alpha3Code,
            region = from.region,
            subregion = from.subregion,
            population = from.population,
            latitude = from.latitude,
            longitude = from.longitude,
        )
}
