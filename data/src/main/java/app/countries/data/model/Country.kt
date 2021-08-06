package app.countries.data.model

data class Country(
    val id: String = "",
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
