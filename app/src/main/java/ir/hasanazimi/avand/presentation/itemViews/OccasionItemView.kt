package ir.hasanazimi.avand.presentation.itemViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hasanazimi.avand.data.entities.local.other.OccasionsOfTheDayEntity
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import ir.hasanazimi.avand.presentation.theme.RedColor
import ir.hasanazimi.avand.presentation.theme.TransparentlyBlack


@Composable
fun OccasionItemView(occasionsOfTheDay: OccasionsOfTheDayEntity) {
    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = occasionsOfTheDay.occasionText,
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth(),
            fontSize = 12.sp,
            style = CustomTypography.labelLarge
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "location",
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(16.dp)
                .weight(0.2f)
                .fillMaxWidth(),
            tint = if (occasionsOfTheDay.isHoliday) RedColor else TransparentlyBlack
        )
    }
}


@Preview(showBackground = true)
@Composable
fun OccasionItemViewPreview(){
    AvandTheme {
        OccasionItemView(occasionsOfTheDay = OccasionsOfTheDayEntity("روز بزرگداشت فردوسی", isHoliday = true))
    }
}
