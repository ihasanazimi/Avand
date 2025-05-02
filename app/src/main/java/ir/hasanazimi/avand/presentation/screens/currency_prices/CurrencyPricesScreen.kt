package ir.hasanazimi.avand.presentation.screens.currency_prices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.data.fakeBitPriceList
import ir.hasanazimi.avand.data.fakeCurrencyPriceList
import ir.hasanazimi.avand.data.fakeGoldPriceList
import ir.hasanazimi.avand.presentation.itemViews.CurrencyPriceItemView
import ir.hasanazimi.avand.presentation.itemViews.CustomSpacer
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography


@Composable
fun CurrencyPricesScreen(activity: MainActivity , navController: NavHostController) {
    AvandTheme {
        Surface {
            Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp , bottom = 8.dp)) {

                Row(
                    Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {


                    Row {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "refresh prices",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(CircleShape)
                                .size(28.dp)
                                .padding(2.dp)
                                .clickable {
                                    // todo
                                },
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "share prices",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(28.dp)
                                .padding(5.dp)
                                .clickable {
                                    // todo
                                },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = "لیست قیمت ها :",
                        style = CustomTypography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )

                }


                Card(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                        (fakeBitPriceList + fakeGoldPriceList + fakeCurrencyPriceList).forEach { item ->
                            CurrencyPriceItemView(obj = item, modifier = Modifier)
                        }


                        CustomSpacer()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "مشاهده بیشتر",
                                style = CustomTypography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyPricesScreenPreview() {
    AvandTheme {
        CurrencyPricesScreen(
            activity = MainActivity(),
            navController = rememberNavController()
        )
    }
}





