package ir.hasanazimi.avand.presentation.itemViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import ir.hasanazimi.avand.data.entities.local.other.EventOfDayEntity
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography
import ir.hasanazimi.avand.presentation.theme.RedColor
import ir.hasanazimi.avand.presentation.theme.TransparentlyBlack


@Composable
fun EventItemView(eventOfDay: EventOfDayEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = eventOfDay.eventTitle,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(0.9f)
                .fillMaxWidth(),
            fontSize = 12.sp,
            style = CustomTypography.labelLarge,
            maxLines = 3
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(16.dp)
                .weight(0.1f)
                .fillMaxWidth(),
            tint = if (eventOfDay.isHoliday) RedColor else MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.5f
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun OccasionItemViewPreview() {
    AvandTheme {
        Surface {
            EventItemView(eventOfDay = EventOfDayEntity("روز بزرگداشت فردوسی", isHoliday = true))
        }
    }
}
