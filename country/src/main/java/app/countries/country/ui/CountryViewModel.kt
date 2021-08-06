package app.countries.country.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.countries.data.repository.CountryRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CountryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CountryRepositoryInterface,
): ViewModel() {
    private val countryId: String = savedStateHandle.get("countryId")!!

    val state: MutableStateFlow<CountryState> = MutableStateFlow(CountryState.Empty)

    init {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                repository.observe(countryId).collectLatest { country ->
                    state.value = country
                        ?.let { CountryState(country = it) }
                        ?: CountryState.Empty
                }
            }
        }
    }
}
