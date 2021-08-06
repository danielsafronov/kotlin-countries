package app.countries

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import app.countries.country.ui.Country
import app.countries.countrylist.ui.CountryList

internal sealed class Destinations(val path: String) {
    object CountryList: Destinations("countries")
    object CountryDetailed: Destinations("countries/{countryId}") {
        fun createPath(countryId: String): String =
            "countries/${countryId}"
    }
}

@Composable
internal fun CountriesNavigation(
    controller: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = controller,
        startDestination = Destinations.CountryList.path,
    ) {
        composable(Destinations.CountryList.path) {
            CountryList(
                onCountryClick = { countryId ->
                    controller.navigate(
                        Destinations.CountryDetailed.createPath(
                            countryId = countryId,
                        ),
                    )
                }
            )
        }
        composable(
            route = Destinations.CountryDetailed.path,
            arguments = listOf(
                navArgument("countryId") { type = NavType.StringType }
            )
        ) {
            Country(
                navigateUp = controller::navigateUp,
            )
        }
    }
}
