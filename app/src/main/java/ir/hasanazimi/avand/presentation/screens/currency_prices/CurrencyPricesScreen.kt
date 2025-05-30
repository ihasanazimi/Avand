package ir.hasanazimi.avand.presentation.screens.currency_prices

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.MainActivity
import ir.hasanazimi.avand.common.extensions.showToast
import ir.hasanazimi.avand.common.extensions.withNotNull
import ir.hasanazimi.avand.data.entities.ResponseState
import ir.hasanazimi.avand.data.entities.local.currenciees.CurrencyEntity
import ir.hasanazimi.avand.presentation.itemViews.CurrencyPriceItemView
import ir.hasanazimi.avand.presentation.screens.full_screen_loading.FullScreenLoading
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography


@Composable
fun CurrencyPricesScreen(activity: MainActivity , navController: NavHostController) {

    val TAG = "CurrencyPricesScreen"
    val context = LocalContext.current
    val viewModel = hiltViewModel<CurrencyPricesScreenVM>()
    var currencies by remember { mutableStateOf<List<CurrencyEntity>?>(null) }
    var showLoading by remember { mutableStateOf(false) }

    AvandTheme {
        Surface {

            LaunchedEffect(Unit) {
                viewModel.getCurrencies()

                viewModel.currencies.collect { result ->
                    when(result){
                        is ResponseState.Success -> {
                            Log.i(TAG, "CurrencyPricesScreen: ${result.data}")
                            currencies = result.data
                            showLoading = false

                        }

                        is ResponseState.Loading -> {
                            showLoading = true
                        }

                        is ResponseState.Error -> {
                            showLoading = false
                            showToast(context,"خطای نامشخص")
                        }
                    }
                }
            }


            FullScreenLoading(showLoading)

            if (currencies?.isNotEmpty() == true){
                SuccessScreenOfCurrencies(currencies)
            }
        }
    }
}

@Composable
private fun SuccessScreenOfCurrencies(listOfCurrencies : List<CurrencyEntity>?) {

    listOfCurrencies.withNotNull { list ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {

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
                LazyColumn(Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {

                    items(list){ item ->
                        CurrencyPriceItemView(obj = item, modifier = Modifier)
                    }

                    /*CustomSpacer()

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
                    }*/
                }
            }
        }

    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CurrencyPricesScreenPreview() {
    AvandTheme {
        CurrencyPricesScreen(
            activity = MainActivity(),
            navController = rememberNavController()
        )
    }
}





