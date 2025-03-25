package ir.ha.goodfeeling.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.ha.goodfeeling.R

// Set of Material typography styles to start with

val CustomFontFamily = FontFamily(
    Font(R.font.my_font, FontWeight.Normal)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = Color.DarkGray
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.Black
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        color = Color.Gray
    )
)