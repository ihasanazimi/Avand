package ir.hasanazimi.avand.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import ir.hasanazimi.avand.R

// Set of Material typography styles to start with


val vazirFont = FontFamily(
    Font(R.font.yekan, FontWeight.Normal),
)


val CustomTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = vazirFont,
        fontSize = 24.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),
    bodyLarge = TextStyle(
        fontFamily = vazirFont,
        fontSize = 18.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),
    labelLarge = TextStyle(
        fontFamily = vazirFont,
        fontSize = 16.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),
    labelSmall = TextStyle(
        fontFamily = vazirFont,
        fontSize = 14.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),

)