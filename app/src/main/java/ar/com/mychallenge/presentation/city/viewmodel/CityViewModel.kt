package ar.com.mychallenge.presentation.city.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.util.Result
import ar.com.mychallenge.data.repository.CityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CityViewModel(
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _citiesState = MutableStateFlow<Result<List<City>>>(Result.Loading)
    val citiesState: StateFlow<Result<List<City>>> = _citiesState.asStateFlow()

    init {
        loadCities()
    }

    fun loadCities() {
        viewModelScope.launch {
            cityRepository.getCities().collect { result ->
                _citiesState.value = result
            }
        }
    }
}