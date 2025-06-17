package ar.com.mychallenge.presentation.city.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xfff5fa38)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "${city.name} - ${city.country}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Coord: lat-${city.coord?.lat} long-${city.coord?.lon}", style = MaterialTheme.typography.bodySmall)

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
            {}
        )
    }
}