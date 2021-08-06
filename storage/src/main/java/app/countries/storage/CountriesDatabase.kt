package app.countries.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import app.countries.storage.dao.CountryDao
import app.countries.storage.entities.Country

@Database(
    entities = [
        Country::class,
    ],
    version = 1,
)
abstract class CountriesDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao
}
