package app.countries.data

import app.countries.data.mapper.CountryEntityToCountyMapper
import app.countries.data.mapper.CountryNetworkDtoToCountyMapper
import app.countries.data.mapper.CountryToCountyEntityMapper
import app.countries.data.repository.*
import app.countries.network.CountrySDKInterface
import app.countries.storage.dao.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    @Singleton
    internal fun provideLocalCountryRepository(
        dao: CountryDao,
        countryMapper: CountryToCountyEntityMapper,
        countryEntityMapper: CountryEntityToCountyMapper,
    ): LocalCountryRepositoryInterface =
        LocalCountryRepository(
            dao = dao,
            countryMapper = countryMapper,
            countryEntityMapper = countryEntityMapper,
        )

    @Provides
    @Singleton
    internal fun provideRemoteCountryRepository(
        sdk: CountrySDKInterface,
        countryNetworkDtoMapper: CountryNetworkDtoToCountyMapper,
    ): RemoteCountryRepositoryInterface = RemoteCountryRepository(
        sdk = sdk,
        countryNetworkDtoMapper = countryNetworkDtoMapper,
    )

    @Provides
    @Singleton
    internal fun provideCountryRepository(
        localRepository: LocalCountryRepositoryInterface,
        remoteRepository: RemoteCountryRepositoryInterface,
    ): CountryRepositoryInterface =
        CountryRepository(
            localRepository = localRepository,
            remoteRepository = remoteRepository,
        )
}
