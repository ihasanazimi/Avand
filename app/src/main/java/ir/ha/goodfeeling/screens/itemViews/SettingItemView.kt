package ir.ha.goodfeeling.screens.itemViews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.navigation.Screens
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme


data class SettingItem(
    val icon: ImageVector,
    val title: String,
    val type: Screens?,
)


val settingItems: ArrayList<SettingItem> = arrayListOf(
    SettingItem(Icons.Default.Settings, "اطلاعات حساب کاربری", Screens.Setting),
    SettingItem(Icons.Default.AccountBox, "درباره توسعه دهنده", Screens.AboutUs),
    SettingItem(Icons.Default.Info, "نسخه 1.1.0", null)
)


@Composable
fun SettingItemView(
    settingItem: SettingItem,
    onItemClicked: (type : Screens?) -> Unit = {}
) {

    Card(
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable {
                // todo
            },
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(settingItem.type) }) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "account",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )

                    Row(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 4.dp)
                            .weight(0.8f),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = settingItem.title,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .fillMaxWidth()
                                .weight(0.8f),
                        )

                        Icon(
                            imageVector = settingItem.icon,
                            contentDescription = "account",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(24.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            /*CustomSpacer(Modifier.padding(horizontal = 4.dp, vertical = 2.dp))*/
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SettingItemViewPreview() {
    GoodFeelingTheme {
        LazyColumn {
            items(settingItems) {
                SettingItemView(it)
            }
        }
    }
}