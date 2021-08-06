package app.countries.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.countries.storage.entities.Country
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun store(country: Country)

    @Query("SELECT * FROM country ORDER BY name")
    abstract fun observe(): Flow<List<Country>>

    @Query("SELECT * FROM country WHERE id = :id LIMIT 1")
    abstract fun observe(id: String): Flow<Country?>
}
