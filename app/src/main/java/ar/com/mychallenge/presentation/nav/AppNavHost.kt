package ar.com.mychallenge.presentation.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.presentation.city.ui.CityDetailScreen
import ar.com.mychallenge.presentation.city.ui.CityListScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CityAppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "cityList") {
        composable("cityList") {
            CityListScreen(
                onCityClick = { city ->
                    val cityJson = Json.encodeToString(city)
                    val encodedCityJson = URLEncoder.encode(cityJson, StandardCharsets.UTF_8.toString())
                    navController.navigate("cityDetail/$encodedCityJson")
                }
            )
        }
        composable(
            "cityDetail/{cityJson}",
            arguments = listOf(navArgument("cityJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityJson = backStackEntry.arguments?.getString("cityJson")
            val city = cityJson?.let {
                val decodedCity = URLDecoder.decode(cityJson, StandardCharsets.UTF_8.toString())
                Json.decodeFromString<City>(decodedCity)
            }
            city?.let {
                CityDetailScreen(
                    city = city,
                    onBackClick = { navController.popBackStack() }
                )
            } ?: Text("Ups.. puedes volver a intentarlo?", modifier = Modifier.fillMaxSize())
        }
    }
}