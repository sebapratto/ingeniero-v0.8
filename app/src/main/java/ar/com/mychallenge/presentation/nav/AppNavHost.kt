package ar.com.mychallenge.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.com.mychallenge.presentation.city.ui.CityDetailScreen
import ar.com.mychallenge.presentation.city.ui.CityListScreen
import ar.com.mychallenge.presentation.city.ui.CityMapScreen

@Composable
fun CityAppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "cityList") {
        composable("cityList") {
            CityListScreen(
                onCityClick = { city ->
                    navController.navigate("cityDetail/${city.encodeModel()}")
                },
                onMapViewClick = { city ->
                    navController.navigate("cityMapView/${city.encodeModel()}")
                }
            )
        }
        composable(
            "cityDetail/{cityJson}",
            arguments = listOf(navArgument("cityJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityJson = backStackEntry.arguments?.getString("cityJson")
            cityJson?.decodeModel()?.let {
                CityDetailScreen(
                    city = it,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
        composable(
            "cityMapView/{cityJson}",
            arguments = listOf(navArgument("cityJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityJson = backStackEntry.arguments?.getString("cityJson")
            cityJson?.decodeModel()?.let {
                CityMapScreen(
                    city = it,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}