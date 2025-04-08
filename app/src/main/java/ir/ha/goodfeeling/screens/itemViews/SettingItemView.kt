package ir.ha.goodfeeling.screens.itemViews

import android.content.Context
import android.os.Build
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.LightPrimary
import ir.ha.goodfeeling.ui.theme.TransparentlyBlack
import ir.ha.goodfeeling.ui.theme.TransparentlyBlue


data class SettingItem(
    val icon: ImageVector,
    val title: String,
)


val settingItems: ArrayList<SettingItem> = arrayListOf(
    SettingItem(Icons.Default.Settings, "اطلاعات حساب کاربری"),
    SettingItem(Icons.Default.AccountBox, "درباره توسعه دهنده"),
    SettingItem(Icons.Default.AccountBox, "نسخه 1.1.0")
)


@Composable
fun SettingItemView(settingItem: SettingItem) {

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            2.dp,
            TransparentlyBlue
        ),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable {
                // todo
            },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

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
                        tint = LightPrimary.copy(alpha = 0.7f)
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
                            color = TransparentlyBlack
                        )

                        Icon(
                            imageVector = settingItem.icon,
                            contentDescription = "account",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(24.dp),
                            tint = LightPrimary.copy(alpha = 0.7f)
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