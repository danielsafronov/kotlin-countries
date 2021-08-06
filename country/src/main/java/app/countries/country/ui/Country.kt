package app.countries.country.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Country(
    navigateUp: () -> Unit,
) {
    Country(
        viewModel = hiltViewModel(),
        navigateUp = navigateUp,
    )
}

@Composable
internal fun Country(
    viewModel: CountryViewModel,
    navigateUp: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            CountryTopAppBar(
                title = state.country.name ?: "",
                navigateUp = navigateUp
            )
        },
        content = { CountryContent(viewModel = viewModel) },
    )
}

@Composable
internal fun CountryContent(
    viewModel: CountryViewModel,
) {
    val state = viewModel.state.collectAsState().value
    val country = state.country

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        CountryRow(title = "Name", value = country.name ?: "")
        Divider()

        CountryRow(title = "Code", value = country.alpha3Code ?: "")
        Divider()

        CountryRow(title = "Region", value = country.region ?: "")
        Divider()

        CountryRow(title = "Subregion", value = country.subregion ?: "")
        Divider()

        CountryRow(title = "Population", value = country.population?.toString() ?: "")
    }
}

@Composable
internal fun CountryRow(
    title: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 36.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = value,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.body2,
            color = Color.DarkGray,
        )
    }
}

@Composable
internal fun CountryTopAppBar(
    title: String,
    navigateUp: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Up",
                )
            }
        },
    )
}
