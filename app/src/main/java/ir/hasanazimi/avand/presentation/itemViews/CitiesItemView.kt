package ir.hasanazimi.avand.presentation.itemViews

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hasanazimi.avand.data.entities.local.other.CityEntity
import ir.hasanazimi.avand.presentation.theme.CustomTypography

@Composable
fun CitiesItemView(city: CityEntity, onSelectedCity: (city: CityEntity) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(if (city.selected) MaterialTheme.colorScheme.primary.copy(0.2f) else MaterialTheme.colorScheme.background),
        onClick = {
            onSelectedCity.invoke(city)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
            ) {
                Text(
                    text = city.cityName,
                    style = CustomTypography.bodyLarge.copy(
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
private fun CitiesItemViewPreview() {
    CitiesItemView(CityEntity(cityName = "تهران", location = "", selected = false)) {
        Log.i("CitiesItemViewPreview", "CitiesItemViewPreview: ${it.cityName}")
    }
}