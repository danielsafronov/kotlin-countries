package app.countries.countrylist.ui

import app.countries.data.model.Country

/**
 * Country list state.
 */
data class CountryListState(
    val countries: List<Country> = emptyList(),
) {
    companion object {
        val Empty = CountryListState()
    }
}
