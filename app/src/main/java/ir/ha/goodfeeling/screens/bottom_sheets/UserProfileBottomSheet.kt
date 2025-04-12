package ir.ha.goodfeeling.screens.bottom_sheets

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ir.ha.goodfeeling.data.entities.CityEntity
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileBottomSheet(
    citiesSnapshotList: SnapshotStateList<CityEntity>,
    isOpen: Boolean
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var citiesList = remember { citiesSnapshotList }
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
private fun CitiesModalBottomSheetPreview() {
    GoodFeelingTheme {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Search") }
        )
    }
}
