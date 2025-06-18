package ar.com.mychallenge.presentation.city.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.com.mychallenge.R
import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.model.Coord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(city: City, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(end = 25.dp),
                        text = stringResource(R.string.title_list_app),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
                .border(border = BorderStroke(0.dp, Color.Transparent), shape = RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xffffcf00))
                    )
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(city)
                CityProperties(city)
            }
        }
    }
}

@Composable
fun Title(city: City) {
    Text(
        text = "Ciudad",
        style = MaterialTheme.typography.bodySmall,
    )
    Text(
        text = city.name,
        style = MaterialTheme.typography.headlineMedium,
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CityProperties(city: City) {
    Text(
        text = stringResource(R.string.country, city.country),
        style = MaterialTheme.typography.bodyLarge,
    )
    Spacer(modifier = Modifier.height(4.dp))
    city.coord?.let {
        Text(
            text = stringResource(R.string.title_coord),
            style = MaterialTheme.typography.bodyLarge,
        )
        Column {
            Text(
                text = stringResource(R.string.longitud, it.lon),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = stringResource(R.string.latitud, it.lat),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}


@Preview
@Composable
fun PreviewDetail() {
    CityDetailScreen(
        city = City(
            id = 1,
            name = "Yokogawara",
            country = "JP",
            coord = Coord(
                lon = 34.383331,
                lat = 44.666668
            )
        ),
        {}
    )
}