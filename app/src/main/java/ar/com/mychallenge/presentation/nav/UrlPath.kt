package ar.com.mychallenge.presentation.nav

import ar.com.mychallenge.data.model.City
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun City.encodeModel(): String {
    runCatching {
        val cityJson = Json.encodeToString(this)
        return URLEncoder.encode(cityJson, StandardCharsets.UTF_8.toString())
    }.getOrElse {
        return ""
    }
}

fun String.decodeModel(): City {
    runCatching {
        val decodedCity = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
        return Json.decodeFromString<City>(decodedCity)
    }.getOrElse {
        return City()
    }
}