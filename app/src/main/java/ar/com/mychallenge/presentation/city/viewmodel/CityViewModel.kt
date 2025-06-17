package ar.com.mychallenge.presentation.city.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.repository.CityRepository
import ar.com.mychallenge.data.util.ResultType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CityViewModel(
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _allCities = MutableStateFlow<ResultType<List<City>>>(ResultType.Loading)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val citiesState: StateFlow<ResultType<List<City>>> =
        combine(_allCities, _searchQuery.debounce(300).distinctUntilChanged()) { citiesResult, query ->
            when (citiesResult) {
                is ResultType.Success -> {
                    if (query.isBlank()) {
                        citiesResult
                    } else {
                        val filteredCities = citiesResult.data.filter { city ->
                            city.name.contains(query, ignoreCase = true) ||
                                    city.country.contains(query, ignoreCase = true)
                        }
                        ResultType.Success(filteredCities)
                    }
                }

                else -> citiesResult
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(300),
            initialValue = ResultType.Loading
        )

    init {
        loadCities()
    }

    fun loadCities() {
        viewModelScope.launch {
            cityRepository.getCities().collect { result ->
                _allCities.value = result
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}