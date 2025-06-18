package ar.com.mychallenge.presentation.city.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.com.mychallenge.R
import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.util.ResultType
import ar.com.mychallenge.presentation.city.viewmodel.CityViewModel
import ar.com.mychallenge.presentation.component.BottomBarCustom
import ar.com.mychallenge.presentation.component.MapComponent
import ar.com.mychallenge.presentation.component.TopAppBarCustom
import org.koin.androidx.compose.koinViewModel

@Composable
fun CityListScreen(
    viewModel: CityViewModel = koinViewModel(),
    onCityClick: (City) -> Unit,
    onMapViewClick: (City) -> Unit,
) {
    val citiesState by viewModel.citiesState.collectAsState()
    val citiesFavoritesState by viewModel.citiesFavorites.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val cityMap by viewModel.cityMap.collectAsState()
    var isFavorites by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBarCustom(24.sp)
        },
        bottomBar = {
            if (!isLandscape) {
                BottomBarCustom(
                    onFavoritesClick = { isFavorites = true },
                    onSearchClick = { isFavorites = false },
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    viewModel.onSearchQueryChanged(query)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text(stringResource(R.string.search_city)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray,
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = Color.Gray,
                            )
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )
            if (isLandscape) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.weight(0.5f)
                            .padding(end = 8.dp)
                    ) {
                        CityListContent(
                            citiesState,
                            citiesFavoritesState,
                            isFavorites,
                            searchQuery,
                            isLandscape,
                            viewModel,
                            onCityClick,
                            viewModel::loadCities,
                            onMapViewClick,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        cityMap?.let { city ->
                            MapComponent(city)
                        } ?: Text(
                            text = stringResource(R.string.select_map),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            } else {
                CityListContent(
                    citiesState,
                    citiesFavoritesState,
                    isFavorites,
                    searchQuery,
                    isLandscape,
                    viewModel,
                    onCityClick,
                    viewModel::loadCities,
                    onMapViewClick,
                )
            }
        }
    }
}


@Composable
fun CityListContent(
    citiesState: ResultType<List<City>>,
    citiesFavoritesState: List<City>,
    isFavorites: Boolean,
    searchQuery: String,
    isLandscape: Boolean,
    viewModel: CityViewModel,
    onCityClick: (City) -> Unit,
    onRetryClick: () -> Unit,
    onMapViewClick: (City) -> Unit,
){
    if (isFavorites) {
        if (citiesFavoritesState.isEmpty() && searchQuery.isBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.not_found), style = MaterialTheme.typography.bodyLarge)
            }
        } else if (citiesFavoritesState.isEmpty() && searchQuery.isNotBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.search_not_found, searchQuery),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(citiesFavoritesState) { city ->
                    CityItem(
                        city = city,
                        onClick = { onCityClick(city) },
                        onMapViewClick = {
                            if (isLandscape) {
                                viewModel.setCityMap(it)
                            } else {
                                onMapViewClick(city)
                            }
                        },
                        onFavoritesClick = { viewModel.onFavorites(city) }
                    )
                }
            }
        }
    }
    else {
        when (citiesState) {
            is ResultType.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ResultType.Success -> {
                val cities = citiesState.data
                if (cities.isEmpty() && searchQuery.isBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.not_found), style = MaterialTheme.typography.bodyLarge)
                    }
                } else if (cities.isEmpty() && searchQuery.isNotBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.search_not_found, searchQuery),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(cities) { city ->
                            CityItem(
                                city = city,
                                onClick = { onCityClick(city) },
                                onMapViewClick = {
                                    if (isLandscape) {
                                        viewModel.setCityMap(it)
                                    } else {
                                        onMapViewClick(city)
                                    }
                                },
                                onFavoritesClick = { viewModel.onFavorites(city) }
                            )
                        }
                    }
                }
            }
            is ResultType.Error -> {
                val error = (citiesState as ResultType.Error)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.error, error.message),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Button(onClick = { onRetryClick() }) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }
            }
        }
    }

}