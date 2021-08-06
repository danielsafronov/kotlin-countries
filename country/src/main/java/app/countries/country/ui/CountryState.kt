package app.countries.country.ui

import app.countries.data.model.Country

data class CountryState(
    val country: Country = Country.Empty,
) {
    companion object {
        val Empty: CountryState = CountryState()
    }
}
