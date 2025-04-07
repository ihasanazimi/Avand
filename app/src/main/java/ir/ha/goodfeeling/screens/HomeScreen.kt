package ir.ha.goodfeeling.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.RedColor
import ir.ha.goodfeeling.ui.theme.TransparentlyBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 8.dp)) {
        Widgets()
        CurrencyPricesScreen()
    }
}


data class OccasionsOfTheDay(var occasionText: String = "", var isHoliday: Boolean = false)

val occasionsOfTheDayList: ArrayList<OccasionsOfTheDay> = arrayListOf<OccasionsOfTheDay>(
    OccasionsOfTheDay("روز بزرگداشت فردوسی", isHoliday = true),
    OccasionsOfTheDay("روز جوان", isHoliday = false),
    OccasionsOfTheDay("روز مهندس", isHoliday = false),
)

@Composable
fun OccasionItemView(occasionsOfTheDay: OccasionsOfTheDay, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = occasionsOfTheDay.occasionText,
            modifier = modifier
                .weight(0.8f)
                .fillMaxWidth(),
            fontSize = 12.sp,
            style = CustomTypography.labelLarge
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "location",
            modifier = modifier
                .padding(horizontal = 4.dp)
                .size(16.dp)
                .weight(0.2f)
                .fillMaxWidth(),
            tint = if (occasionsOfTheDay.isHoliday) RedColor else TransparentlyBlack
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GoodFeelingTheme {
        val navController = rememberNavController()
        HomeScreen(Modifier, navController)
    }
}