package ir.ha.goodfeeling.screens.itemViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.common.extensions.getAmountFormatBySeparator
import ir.ha.goodfeeling.data.CurrencyType
import ir.ha.goodfeeling.data.entities.CurrencyPriceEntity
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.GreenColor
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue


@Composable
fun CurrencyPriceItemView(obj: CurrencyPriceEntity, modifier: Modifier = Modifier) {

    GoodFeelingTheme {
        Row(
            modifier = modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {

            Box(contentAlignment = Alignment.CenterStart, modifier = modifier.weight(1.4f)) {
                Text(
                    text = obj.currencyPrice.getAmountFormatBySeparator() + " " + obj.currencyUnitType.unitType,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    style = CustomTypography.labelLarge,
                    modifier = modifier
                        .padding(4.dp)
                )
            }

            Box(
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.3f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = obj.currencyChangePercent + " % ",
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
}


@Composable
fun CustomSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .background(
                TransparentlyBlue,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(1.dp)
    )
}


@Preview
@Composable
fun CurrencyPriceItemViewPreview() {
    GoodFeelingTheme {
        CurrencyPriceItemView(
            CurrencyPriceEntity(
                currencyName = "بیت کوین",
                currencyFlagId = R.drawable.bitcoin,
                currencyPrice = "103,250",
                currencyChangePercent = "3.1" + " % ",
                currencyChangePercentColor = GreenColor,
                currencyUnitType = CurrencyType.Toman
            ),
            modifier = Modifier
        )
    }
}