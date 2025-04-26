package ir.hasanazimi.avand.presentation.bottom_sheets

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hasanazimi.avand.data.getFakeCitiesList
import ir.hasanazimi.avand.data.entities.local.other.CityEntity
import ir.hasanazimi.avand.presentation.itemViews.CitiesItemView
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesModalBottomSheet(
    isOpen: Boolean,
    selectedCity: CityEntity?,
    citiesSnapshotList: SnapshotStateList<CityEntity>,
    onDismiss: (city: CityEntity?) -> Unit,
    onFinallySelectedCity: (city: CityEntity?) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val lazyListState = rememberLazyListState()

    var citiesList = remember { citiesSnapshotList }
    var tempCitySelection by remember { mutableStateOf<CityEntity?>(null) }


    LaunchedEffect(isOpen) {
        if (isOpen) {
            sheetState.expand()
        }
    }

    if (isOpen) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    onDismiss.invoke(selectedCity)
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    CitiesLazyColumn(
                        citiesSnapshotList,
                        lazyListState
                    ) { updatedList ->
                        citiesList = updatedList
                        tempCitySelection = citiesList.find { it.selected }
                    }

                    Button(
                        enabled = tempCitySelection != null || selectedCity != null,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        onClick = {
                            onFinallySelectedCity.invoke(citiesList.find { it.selected })
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
private fun CitiesLazyColumn(
    citiesState: SnapshotStateList<CityEntity>,
    lazyListState: LazyListState,
    updatedList: (updatedList: SnapshotStateList<CityEntity>) -> Unit
) {

    val rememberCoroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = "شهر مورد نظر را انتخاب کنید",
            style = CustomTypography.labelLarge,
            textAlign = TextAlign.End
        )
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(220.dp)
    ) {
        itemsIndexed(citiesState) { index, city ->
            CitiesItemView(city) { selectedItem ->
                citiesState.forEachIndexed { i, _ ->
                    citiesState[i] =
                        citiesState[i].copy(selected = citiesState[i].cityName == selectedItem.cityName)
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
    AvandTheme {
        CitiesModalBottomSheet(
            citiesSnapshotList = getFakeCitiesList(),
            isOpen = true,
            onDismiss = {},
            selectedCity = null
        ) {
            Log.i("CitiesModalBottomSheetPreview", "CitiesModalBottomSheetPreview: ${it?.cityName}")
        }
    }
}
