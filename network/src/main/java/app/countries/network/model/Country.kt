package app.countries.network.model

data class Country(
    val name: String? = null,
    val alpha3Code: String? = null,
    val region: String? = null,
    val subregion: String? = null,
    val population: Long? = null,
    val latlng: List<Double>? = null,
)

typealias CountryNetworkDto = Country
