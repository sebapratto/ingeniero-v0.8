package ar.com.mychallenge.data.remote

import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.util.ResultType
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.util.Locale

class CityApiService(
    private val client: HttpClient,
    private val jsonSerializer: Json,
) {

    suspend fun getCities() = downloadAndParseCitiesDirectly(PATH)

    private suspend fun downloadAndParseCitiesDirectly(url: String): ResultType<Any> {
        return withContext(Dispatchers.IO) {
            val response = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HOST
                    path(url)
                }
            }
            runCatching {
                if (response.status.isSuccess()) {
                    val responseBody = response.bodyAsText()
                    val cities = jsonSerializer.decodeFromString<List<City>>(responseBody).sortedBy {
                        it.name.toLowerCase(Locale.ROOT)
                    }
                    ResultType.Success(cities)
                } else {
                    ResultType.Error(message = "Error en la descarga ${response.status}")
                }
            }.getOrElse {
                ResultType.Error(message = "Error en la descarga")
            }
        }
    }

    companion object {
        private const val PATH = "/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json"
        private const val HOST = "gist.githubusercontent.com"
    }
}