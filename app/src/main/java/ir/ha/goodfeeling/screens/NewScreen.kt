package ir.ha.goodfeeling.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.ha.goodfeeling.data.getNewsFakeData
import ir.ha.goodfeeling.screens.itemViews.NewsItemView
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@Composable
fun NewsScreen() {
    GoodFeelingTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            getNewsFakeData().forEach {
                NewsItemView(it)
            }
        }
    }
}


@Preview
@Composable
private fun NewsScreenPreview() {
    GoodFeelingTheme {
        NewsScreen()
    }
}