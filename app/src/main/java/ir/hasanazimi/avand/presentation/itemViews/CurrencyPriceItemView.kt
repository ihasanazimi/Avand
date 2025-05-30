package ir.hasanazimi.avand.presentation.itemViews

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hasanazimi.avand.R
import ir.hasanazimi.avand.common.extensions.getAmountFormatBySeparator
import ir.hasanazimi.avand.data.entities.local.currenciees.CurrencyEntity
import ir.hasanazimi.avand.data.entities.sealed_enums.CurrencyEnum
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import kotlin.math.roundToInt


@Composable
fun CurrencyPriceItemView(obj: CurrencyEntity, modifier: Modifier = Modifier) {

    val foundItem = CurrencyEnum.fromCode(obj.enumName)

    AvandTheme {
        Row(
            modifier = modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {

            Box(contentAlignment = Alignment.CenterStart, modifier = modifier.weight(1.4f)) {
                var p = obj.value
                /*p = p / 0.0000012195*/
                val newP = String.format("%.6f", p)
                Text(
                    text = newP.toString().getAmountFormatBySeparator() + " " + foundItem?.currencyType?.unitType,
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
                    text = "${foundItem?.code}",
                    modifier = modifier.align(Alignment.CenterStart),
                    maxLines = 1,
                    style = CustomTypography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }

            Row(modifier = modifier.weight(1f), horizontalArrangement = Arrangement.End) {

                Text(
                    text = foundItem?.displayNameFa?:"",
                    style = CustomTypography.labelLarge,
                    maxLines = 1,
                    modifier = modifier
                        .padding(4.dp)
                )


                Image(
                    painter = painterResource(id = foundItem?.icon?:R.drawable.england),
                    contentDescription = foundItem?.code,
                    modifier = modifier
                        .size(28.dp)
                        .padding(4.dp)
                )
            }


        }

//        when (obj.currencyName) {
//            "تتر" -> {
//                CustomSpacer()
//            }
//
//            "سکه امامی" -> {
//                CustomSpacer()
//            }
//        }
    }
}


@Composable
fun CustomSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(1.dp)
    )
}


@Preview
@Composable
fun CurrencyPriceItemViewPreview() {
    AvandTheme {
        Surface {
            /*CurrencyPriceItemView(
                CurrencyPriceEntity(
                    currencyName = "بیت کوین",
                    currencyFlagId = R.drawable.bitcoin,
                    currencyPrice = "103,250",
                    currencyChangePercent = "3.1" + " % ",
                    currencyChangePercentColor = GreenColor,
                    currencyUnitType = CurrencyType.Toman
                ),
                modifier = Modifier
            )*/
        }
    }
}