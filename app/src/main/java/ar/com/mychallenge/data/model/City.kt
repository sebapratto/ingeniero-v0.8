package ar.com.mychallenge.data.model

import kotlinx.serialization.Serializable


@Serializable
data class City(
    val id: Int,
    val name: String,
    val country: String,
    val population: Long
)