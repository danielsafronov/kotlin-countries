package app.countries.countrylist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.countries.base.Result
import app.countries.data.repository.CountryRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CountryListViewModel @Inject constructor(
    private val repository: CountryRepositoryInterface,
) : ViewModel() {
    val state: MutableStateFlow<CountryListState> = MutableStateFlow(CountryListState.Empty)

    init {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                repository.observe().collectLatest { result ->
                    state.value = when (result) {
                        is Result.Success -> CountryListState(countries = result.value)
                        is Result.Error -> CountryListState.Empty
                    }
                }
            }
        }
    }
}
