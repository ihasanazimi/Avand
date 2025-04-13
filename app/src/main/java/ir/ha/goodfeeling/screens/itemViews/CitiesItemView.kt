package ir.ha.goodfeeling.screens.itemViews

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.data.entities.CityEntity
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.DarkBackground
import ir.ha.goodfeeling.ui.theme.LightBackground
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue

@Composable
fun CitiesItemView(city: CityEntity, onSelectedCity: (city: CityEntity) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        border = BorderStroke(if (city.selected) 3.dp else 0.dp, LightPrimary),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) DarkBackground else LightBackground),
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
                        color = if (city.selected) LightPrimary else (if (isSystemInDarkTheme()) Color.White else Color.Black)
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