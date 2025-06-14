package ar.com.mychallenge.data.repository

import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.util.Result
import ar.com.mychallenge.data.remote.CityApiService
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CityRepositoryImpl(
    private val apiService: CityApiService
) : CityRepository {

    override fun getCities(): Flow<Result<List<City>>> = flow {
        emit(Result.Loading)

        try {
            val cities = apiService.getCities()
            emit(Result.Success(cities))
        } catch (e: IOException) {
            emit(Result.Error(e, "Network error. Please check your internet connection."))
        } catch (e: Exception) {
            emit(Result.Error(e, "Failed to load cities: ${e.localizedMessage ?: e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}