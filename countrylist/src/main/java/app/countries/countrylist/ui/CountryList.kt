package app.countries.countrylist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import app.countries.data.model.Country
import app.countries.ui.theme.CountriesTheme
import java.time.format.TextStyle

@Composable
fun CountryList(
    onCountryClick: (countryId: String) -> Unit,
) {
    CountryList(
        viewModel = hiltViewModel(),
        onCountryClick = onCountryClick,
    )
}

@Composable
internal fun CountryList(
    viewModel: CountryListViewModel,
    onCountryClick: (countryId: String) -> Unit,
) {
    Scaffold(
        topBar = { CountryListTopAppBar(title = "Countries") },
        content = {
            CountryListContent(
                viewModel = viewModel,
                onCountryClick = onCountryClick,
            )
        },
    )
}

@Composable
internal fun CountryListContent(
    viewModel: CountryListViewModel,
    onCountryClick: (countryId: String) -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    LazyColumn {
        items(state.countries) { country ->
            CountryListItem(
                country = country,
                onCountryClick = onCountryClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CountryListItem(
    country: Country,
    onCountryClick: (countryId: String) -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable { onCountryClick(country.id) },
        text = {
            Text(text = country.name ?: "")
        },
        secondaryText = {
            Text(text = country.population?.toString() ?: "")
        },
        trailing = {
            Text(
                text = country.alpha3Code ?: "",
                color = Color.DarkGray,
            )
        },
    )
}

@Composable
internal fun CountryListTopAppBar(
    title: String,
) {
    TopAppBar(
        title = { Text(text = title) },
    )
}
