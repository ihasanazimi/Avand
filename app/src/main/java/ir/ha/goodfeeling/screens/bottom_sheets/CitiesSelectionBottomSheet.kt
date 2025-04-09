package ir.ha.goodfeeling.screens.bottom_sheets

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.data.cities
import ir.ha.goodfeeling.data.entities.CityEntity
import ir.ha.goodfeeling.screens.itemViews.CitiesItemView
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesModalBottomSheet(
    citiesSnapshotList: SnapshotStateList<CityEntity>,
    isOpen : Boolean,
    onSelectedCity: (city: CityEntity) -> Unit
) {
    val sheetState =  rememberModalBottomSheetState()
    var citiesList = remember { citiesSnapshotList }
    val context = LocalContext.current

    if (isOpen) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ModalBottomSheet(
                onDismissRequest = {  },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    CitiesModal(citiesList) { updatedList -> citiesList = updatedList }

                    Button(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        onClick = {
                            onSelectedCity.invoke(
                                citiesList.find { it.selected }!!
                            )
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

/*
    Button(onClick = {
        isSheetOpen = true
    }) {
        Text("باز کردن Bottom Sheet")
    }*/

}

@Composable
private fun CitiesModal(
    citiesState: SnapshotStateList<CityEntity>,
    updatedList: (updatedList: SnapshotStateList<CityEntity>) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth().height(250.dp)
    ) {
        items(citiesState) { city ->
            CitiesItemView(city) { selectedCity ->
                citiesState.forEachIndexed { i, _ ->
                    citiesState[i] =
                        citiesState[i].copy(selected = citiesState[i].cityName == selectedCity.cityName)
                }
                updatedList.invoke(citiesState)
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
            Log.i("CitiesModalBottomSheetPreview", "CitiesModalBottomSheetPreview: ${it.cityName}")
        }
    }
}
