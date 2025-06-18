package ar.com.mychallenge.presentation.city.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.com.mychallenge.data.model.City
import ar.com.mychallenge.data.model.Coord
import ar.com.mychallenge.ui.theme.MyChallengeTheme

@Composable
fun CityItem(
    city: City,
    onClick: () -> Unit,
    onMapViewClick: (City) -> Unit,
    onFavoritesClick: (City) -> Boolean,
) {
    var action by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xffffffff)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${city.name} - ${city.country}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Coord: lat-${city.coord?.lat} long-${city.coord?.lon}",
                style = MaterialTheme.typography.bodySmall
            )

            Row {
                IconButton(onClick = { action = onFavoritesClick(city) }) {
                    Icon(
                        if (action) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder ,
                        contentDescription = "like",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { onMapViewClick(city) },
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
                    ),
                    modifier = Modifier.padding(6.dp).width(35.dp).height(35.dp),
                    contentPadding = PaddingValues(2.dp),
                    enabled = true
                ) {
                    Icon(Icons.Filled.Map, contentDescription = "map")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCityItem() {
    MyChallengeTheme {
        CityItem(
            city = City(
                id = 1,
                name = "Yokogawara",
                country = "JP",
                coord = Coord(
                    lon = 34.383331,
                    lat = 44.666668
                )
            ),
            {},
            {},
            { false },
        )
    }
}