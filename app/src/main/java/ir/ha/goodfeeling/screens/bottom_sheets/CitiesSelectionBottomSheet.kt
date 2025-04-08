package ir.ha.goodfeeling.screens.bottom_sheets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.R
import ir.ha.goodfeeling.data.entities.CurrencyPriceEntity
import ir.ha.goodfeeling.screens.itemViews.CurrencyPriceItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.GreenColor
import ir.ha.goodfeeling.ui.theme.TransparentlyBlack
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesModalBottomSheet(
    bottomSheetState: SheetState = rememberModalBottomSheetState(),
    isOpen: Boolean = false
) {
    val sheetState = bottomSheetState
    val coroutineScope = rememberCoroutineScope()

    var isSheetOpen by remember { mutableStateOf(isOpen) }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            sheetState = sheetState,
        ) {
            CitiesModalBottomSheet()
        }
    }


    Button(onClick = {
        isSheetOpen = true
    }) {
        Text("باز کردن Bottom Sheet")
    }

}

@Composable
fun CitiesModalBottomSheet(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        items(cities) {
            CitiesItem(it)
        }
    }
}


@Composable
fun CitiesItem(city: CityEntity) {
    Surface {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            /*border = BorderStroke(1.dp, TransparentlyBlack),*/
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    Text(
                        text = city.cityName,
                        style = CustomTypography.bodyLarge.copy(
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

data class CityEntity(var cityName: String, var location: String, var selected: Boolean)

val cities: ArrayList<CityEntity> = arrayListOf<CityEntity>(
    CityEntity(cityName = "تهران", location = "", selected = false),
    CityEntity(cityName = "کاشان", location = "", selected = false),
    CityEntity(cityName = "اصفهان", location = "", selected = false),
    CityEntity(cityName = "اهواز", location = "", selected = false),
    CityEntity(cityName = "مازندران", location = "", selected = false),
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPersistentBottomSheet() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 60.dp,
        sheetContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("محتوای Bottom Sheet")
                Button(onClick = {
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }) {
                    Text("نیمه بازش کن")
                }
            }
        }
    ) {
        // محتوای اصلی صفحه
        Column(modifier = Modifier.padding(16.dp)) {
            Text("صفحه اصلی")
            Button(onClick = {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }) {
                Text("کامل بازش کن")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
private fun MyModalBottomSheetPreview() {
    GoodFeelingTheme {
        /*CitiesItem("تهران")*/
        CitiesModalBottomSheet(isOpen = true)
    }
}
