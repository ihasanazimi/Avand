package ir.ha.goodfeeling.screens

import androidx.compose.foundation.BorderStroke
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
import ir.ha.goodfeeling.data.bitPriceList
import ir.ha.goodfeeling.data.currencyPriceList
import ir.ha.goodfeeling.data.entities.CurrencyPriceEntity
import ir.ha.goodfeeling.data.goldPriceList
import ir.ha.goodfeeling.screens.itemViews.CurrencyPriceItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue
import ir.ha.goodfeeling.ui.theme.TransparentlyGray


@Composable
fun CurrencyPricesScreen() {
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
                        .background(TransparentlyBlue)
                        .size(28.dp)
                        .padding(2.dp)
                        .clickable {
                            // todo
                        },
                    tint = LightPrimary
                )
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "refresh weather",
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(TransparentlyBlue)
                        .size(28.dp)
                        .padding(5.dp)
                        .clickable {
                            // todo
                        },
                    tint = LightPrimary
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
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            ), border = BorderStroke(
                2.dp,
                TransparentlyBlue
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


@Preview(showBackground = true)
@Composable
fun CurrencyPricesScreenPreview() {
    GoodFeelingTheme {
        CurrencyPricesScreen()
    }
}





