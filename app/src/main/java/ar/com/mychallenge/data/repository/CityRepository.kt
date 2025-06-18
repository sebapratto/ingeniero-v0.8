package ar.com.mychallenge.data.repository

import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.util.ResultType
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun getCities(): Flow<ResultType<List<City>>>
}