package ir.ha.goodfeeling.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.RedColor
import ir.ha.goodfeeling.ui.theme.TransparentlyBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    Column(
        modifier = modifier
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp)
            ) {
                Text(
                    text = "سـلام",
                    style = CustomTypography.labelSmall,
                    modifier = Modifier.align(Alignment.End)
                )
                Text(
                    text = "حسن عظیمی",
                    style = CustomTypography.titleLarge
                )
            }


            MyLottieAnimation()
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Widgets(
                Modifier.offset(
                    y = (-24).dp
                )
            )
            CurrencyPricesScreen(
                Modifier.offset(
                    y = (-24).dp
                )
            )
        }
    }
}


data class OccasionsOfTheDay(var occasionText: String = "", var isHoliday: Boolean = false)

val occasionsOfTheDayList: ArrayList<OccasionsOfTheDay> = arrayListOf<OccasionsOfTheDay>(
    OccasionsOfTheDay("روز بزرگداشت فردوسی", isHoliday = true),
    OccasionsOfTheDay("روز جوان", isHoliday = false),
    OccasionsOfTheDay("روز مهندس", isHoliday = false),
)

@Composable
fun OccasionItemView(occasionsOfTheDay: OccasionsOfTheDay) {
    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = occasionsOfTheDay.occasionText,
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth(),
            fontSize = 12.sp,
            style = CustomTypography.labelLarge
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "location",
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(16.dp)
                .weight(0.2f)
                .fillMaxWidth(),
            tint = if (occasionsOfTheDay.isHoliday) RedColor else TransparentlyBlack
        )
    }
}


@Composable
fun MyLottieAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.birds)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .height(124.dp)
            .width(220.dp)
            .offset(
                x = (-5).dp,
                y = (-5).dp
            )
            .graphicsLayer(rotationZ = 180f)
            .graphicsLayer(rotationX = 180f)
    )
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GoodFeelingTheme {
        val navController = rememberNavController()
        HomeScreen(Modifier, navController)
    }
}