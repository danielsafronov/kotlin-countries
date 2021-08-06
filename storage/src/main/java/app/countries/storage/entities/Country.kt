package app.countries.storage.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    indices = [
        Index("id", unique = true),
        Index("alpha3Code", unique = true),
    ]
)
data class Country(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String? = null,
    val alpha3Code: String? = null,
    val region: String? = null,
    val subregion: String? = null,
    val population: Long? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
) {
    companion object {
        val Empty: Country = Country()
    }
}

typealias CountryEntity = Country
