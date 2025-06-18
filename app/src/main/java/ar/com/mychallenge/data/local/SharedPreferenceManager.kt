package ar.com.mychallenge.data.local

import android.content.Context
import android.content.SharedPreferences
import ar.com.mychallenge.data.model.City
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.core.content.edit

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("city_prefs", Context.MODE_PRIVATE)

    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        private const val KEY_CITIES_LIST = "cities_list"
    }

    fun saveCities(cities: List<City>) {
        val citiesJsonString = json.encodeToString(cities)
        sharedPreferences.edit { putString(KEY_CITIES_LIST, citiesJsonString) }
    }

    fun getCities(): List<City> {
        val citiesJsonString = sharedPreferences.getString(KEY_CITIES_LIST, null)
        return if (citiesJsonString != null) {
            try {
                json.decodeFromString<List<City>>(citiesJsonString)
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }
}