package com.cobasendiri.youmov.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cobasendiri.youmov.R

val PoppinsFontFamily: FontFamily
    @Composable get() = FontFamily (
        Font(R.font.poppins_bold, weight = FontWeight.Bold),
        Font(R.font.poppins_semibold, weight = FontWeight.SemiBold),
        Font(R.font.poppins_medium, weight = FontWeight.Medium),
        Font(R.font.poppins_regular, weight = FontWeight.Normal)
    )

val baseline = Typography()

@Composable
fun youMovTypography(): Typography{
    val poppinsFontFamily = PoppinsFontFamily
    return Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = poppinsFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = poppinsFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = poppinsFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = poppinsFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = poppinsFontFamily),
        headlineSmall = baseline.headlineSmall.copy(
            fontFamily = poppinsFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        ),
        titleLarge = baseline.titleLarge.copy(fontFamily = poppinsFontFamily),
        titleMedium = baseline.titleMedium.copy(
            fontFamily = poppinsFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        ),
        titleSmall = baseline.titleSmall.copy(
            fontFamily = poppinsFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        ),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = poppinsFontFamily),//16.sp
        bodyMedium = baseline.bodyMedium.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            color = TextSecondary
        ),//14.sp
        bodySmall = baseline.bodySmall.copy(fontFamily = poppinsFontFamily),
        labelLarge = baseline.labelLarge.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = TextMuted
        ),//14.sp
        labelMedium = baseline.labelMedium.copy(
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            color = TextMuted
        ),//12.sp
        labelSmall = baseline.labelSmall.copy(fontFamily = poppinsFontFamily),//11.sp
    )
}