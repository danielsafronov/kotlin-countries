package app.countries.network

import app.countries.network.service.CountryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://restcountries.eu/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    internal fun provideCountryService(retrofit: Retrofit): CountryService =
        retrofit.create(CountryService::class.java)

    @Provides
    @Singleton
    internal fun provideSDK(service: CountryService): CountrySDKInterface =
        CountrySDK(service)
}
