package ir.ha.goodfeeling.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.model.content.CircleShape
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.GreenColor
import ir.ha.goodfeeling.ui.theme.RedColor
import ir.ha.goodfeeling.ui.theme.TransparentlyGray


@Composable
fun CurrencyPricesScreen(modifier: Modifier = Modifier) {
    Column {

        Row(
            Modifier
                .align(Alignment.End)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {


            Row {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "refresh weather",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .background(TransparentlyGray)
                        .size(28.dp)
                        .padding(2.dp)
                        .clickable {
                            // todo
                        },
                    tint = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "refresh weather",
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(TransparentlyGray)
                        .size(28.dp)
                        .padding(5.dp)
                        .clickable {
                            // todo
                        },
                    tint = Color.Black
                )
            }

            Text(
                text = "لیست قیمت ها :",
                style = CustomTypography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

        }


        Card(
            colors = CardColors(
                containerColor = TransparentlyGray,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            )
        ) {
            Column(Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                LazyColumn {
                    items(bitPriceList + goldPriceList + currencyPriceList) { item ->
                        CurrencyPriceItemView(obj = item, modifier = Modifier)
                    }
                }
            }
        }
    }
}


data class CurrencyPriceEntity(
    val currencyName: String,
    val currencyFlagId: Int,
    val currencyPrice: String,
    val currencyChangePercent: String,
    val currencyChangePercentColor: Color,
    val currencyUnitType: String = " تومان "
)

val bitPriceList: ArrayList<CurrencyPriceEntity> = arrayListOf<CurrencyPriceEntity>(
    CurrencyPriceEntity(
        currencyName = "بیت کوین",
        currencyFlagId = R.drawable.bitcoin,
        currencyPrice = "103,250",
        currencyChangePercent = "3.1" + " % ",
        currencyChangePercentColor = GreenColor,
        currencyUnitType = " $ "
    ),
    CurrencyPriceEntity(
        currencyName = "اتریوم",
        currencyFlagId = R.drawable.ethereum,
        currencyPrice = "103,250",
        currencyChangePercent = "1.8" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = " $ "
    ),
    CurrencyPriceEntity(
        currencyName = "تتر",
        currencyFlagId = R.drawable.tether,
        currencyPrice = "103,250",
        currencyChangePercent = "2.5" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = "تومان"
    )
)


val goldPriceList: ArrayList<CurrencyPriceEntity> = arrayListOf<CurrencyPriceEntity>(
    CurrencyPriceEntity(
        currencyName = "طلا 18 عیار",
        currencyFlagId = R.drawable.gold,
        currencyPrice = "8,100,370",
        currencyChangePercent = "5.1" + " % ",
        currencyChangePercentColor = GreenColor,
        currencyUnitType = " تومان "
    ),
    CurrencyPriceEntity(
        currencyName = "سکه امامی",
        currencyFlagId = R.drawable.golden_coin,
        currencyPrice = "98,103,250",
        currencyChangePercent = "1.8" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = " تومان "
    )
)

val currencyPriceList: ArrayList<CurrencyPriceEntity> = arrayListOf<CurrencyPriceEntity>(
    CurrencyPriceEntity(
        currencyName = "دلار",
        currencyFlagId = R.drawable.us,
        currencyPrice = "103,250",
        currencyChangePercent = "3.1" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = "تومان"
    ),
    CurrencyPriceEntity(
        currencyName = "یورو",
        currencyFlagId = R.drawable.euro,
        currencyPrice = "103,250",
        currencyChangePercent = "1.8" + " % ",
        currencyChangePercentColor = GreenColor,
        currencyUnitType = "تومان"
    ),
    CurrencyPriceEntity(
        currencyName = "پوند",
        currencyFlagId = R.drawable.england,
        currencyPrice = "103,250",
        currencyChangePercent = "2.5" + " % ",
        currencyChangePercentColor = RedColor,
        currencyUnitType = "تومان"
    )
)


@Composable
fun CurrencyPriceItemView(obj: CurrencyPriceEntity, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {

        Box(contentAlignment = Alignment.CenterStart, modifier = modifier.weight(1.4f)) {
            Text(
                text = obj.currencyPrice + " " + obj.currencyUnitType,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = CustomTypography.labelLarge,
                modifier = modifier
                    .padding(4.dp)
            )
        }

        Box(
            modifier = modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
                .weight(0.3f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = obj.currencyChangePercent,
                modifier = modifier.align(Alignment.CenterStart),
                color = obj.currencyChangePercentColor,
                maxLines = 1,
                style = CustomTypography.labelSmall,
                textAlign = TextAlign.Center
            )
        }

        Row(modifier = modifier.weight(1f), horizontalArrangement = Arrangement.End) {

            Text(
                text = obj.currencyName,
                style = CustomTypography.labelLarge,
                maxLines = 1,
                modifier = modifier
                    .padding(4.dp)
            )


            Image(
                painter = painterResource(id = obj.currencyFlagId),
                contentDescription = obj.currencyName,
                modifier = modifier
                    .size(28.dp)
                    .padding(4.dp)
            )
        }


    }


    when (obj.currencyName) {
        "تتر" -> {
            CustomSpacer()
        }

        "سکه امامی" -> {
            CustomSpacer()
        }
    }

}


@Composable
fun CustomSpacer() {
    Spacer(
        Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .background(
                TransparentlyGray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(1.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun CurrencyPricesScreenPreview() {
    GoodFeelingTheme {
        CurrencyPricesScreen()
    }
}





