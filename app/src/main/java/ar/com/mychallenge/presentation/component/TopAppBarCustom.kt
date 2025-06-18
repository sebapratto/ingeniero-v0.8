package ar.com.mychallenge.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import ar.com.mychallenge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustom(textSize: TextUnit) {
    TopAppBar(title = { Text(
        modifier = Modifier.fillMaxWidth().padding(end = 25.dp),
        text = stringResource(R.string.title_list_app),
        textAlign = TextAlign.Center,
        fontSize = textSize,
        fontWeight = FontWeight.W300
    ) })
}