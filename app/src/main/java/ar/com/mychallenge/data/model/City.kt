package ar.com.mychallenge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class City(
    @SerialName("_id")
    val id: Int = 0,
    val name: String = "",
    val country: String = "",
    val coord: Coord? = null
)

@Serializable
data class Coord(
    val lon: Double = 0.0,
    val lat: Double = 0.0
)