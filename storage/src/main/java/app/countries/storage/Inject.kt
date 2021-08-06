package app.countries.storage

import android.content.Context
import android.os.Debug
import androidx.room.Room
import app.countries.storage.dao.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    internal fun provideDatabase(
        @ApplicationContext context: Context,
    ): CountriesDatabase {
        val builder = Room.databaseBuilder(context, CountriesDatabase::class.java, "countries.db")
            .fallbackToDestructiveMigration()

        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }

        return builder.build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object DatabaseDaoModule {
    @Provides
    fun provideCountryDao(database: CountriesDatabase): CountryDao =
        database.countryDao()
}
