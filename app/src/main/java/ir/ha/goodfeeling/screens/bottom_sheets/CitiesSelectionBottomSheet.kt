package ir.ha.goodfeeling.screens.bottom_sheets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.data.cities
import ir.ha.goodfeeling.data.entities.CityEntity
import ir.ha.goodfeeling.screens.itemViews.CitiesItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesModalBottomSheet(
    citiesSnapshotList: SnapshotStateList<CityEntity>,
    isOpen: Boolean,
    onSelectedCity: (city: CityEntity?) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var citiesList = remember { citiesSnapshotList }
    val context = LocalContext.current

    val lazyListState = rememberLazyListState()

    if (isOpen) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ModalBottomSheet(
                onDismissRequest = {
                    onSelectedCity.invoke(null)
                },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    CitiesModal(citiesList, lazyListState) { updatedList -> citiesList = updatedList }

                    Button(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        onClick = {
                            val selectedCity = citiesList.find { it.selected }
                            if (selectedCity != null) {
                                onSelectedCity.invoke(selectedCity)
                            }
                        }) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "تایید",
                                style = CustomTypography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
private fun CitiesModal(
    citiesState: SnapshotStateList<CityEntity>,
    lazyListState: LazyListState,
    updatedList: (updatedList: SnapshotStateList<CityEntity>) -> Unit
) {
    
    val rememberCoroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp , vertical = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = "شهر مورد نظر را انتخاب کنید",
            style = CustomTypography.labelLarge,
            textAlign = TextAlign.End
        )
    }

    LazyColumn(
        state = lazyListState, // تخصیص LazyListState به LazyColumn
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(200.dp)
    ) {
        itemsIndexed(citiesState) { index, city ->  // استفاده از itemsIndexed
            CitiesItemView(city) { selectedCity ->
                citiesState.forEachIndexed { i, _ ->
                    citiesState[i] =
                        citiesState[i].copy(selected = citiesState[i].cityName == selectedCity.cityName)
                }
                updatedList.invoke(citiesState)

                rememberCoroutineScope.launch {
                    Log.i("CitiesModal", "CitiesModal: $index")
                    lazyListState.animateScrollToItem(index)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
private fun CitiesModalBottomSheetPreview() {
    GoodFeelingTheme {
        CitiesModalBottomSheet(citiesSnapshotList = cities(), isOpen = true) {
            Log.i("CitiesModalBottomSheetPreview", "CitiesModalBottomSheetPreview: ${it?.cityName}")
        }
    }
}
