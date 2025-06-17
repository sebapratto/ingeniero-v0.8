package ar.com.mychallenge.data.repository

import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.remote.CityApiService
import ar.com.mychallenge.data.util.ResultType
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CityRepositoryImpl(
    private val apiService: CityApiService
) : CityRepository {

    override fun getCities(): Flow<ResultType<List<City>>> = flow {
        emit(ResultType.Loading)

        try {
            when(val result = apiService.getCities()) {
                is ResultType.Success -> {
                    val cities = result.data as List<City>
                    emit(ResultType.Success(cities))
                }
                is ResultType.Error -> {
                    emit(ResultType.Error(message = result.message))
                }
                else -> {}
            }
        } catch (e: IOException) {
            emit(ResultType.Error(e, "Network error. Please check your internet connection."))
        } catch (e: Exception) {
            emit(ResultType.Error(e, "Failed to load cities: ${e.localizedMessage ?: e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}