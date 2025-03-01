package com.sustenanceandroid.composables

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.sp
import com.sustenanceandroid.R
import com.sustenanceandroid.ui.theme.Poppins

object TextStyles {

    @Composable
    fun textDefault() = LocalTextStyle.current.copy(
        fontFamily = FontFamily.SansSerif,
        color = colorResource(R.color.black),
        letterSpacing = (0.175f).sp,
        lineBreak = LineBreak.Simple,
        platformStyle = PlatformTextStyle(
            includeFontPadding = true
        )
    )

    @Composable
    fun headLine() = textDefault().copy(
        fontFamily = Poppins,
        fontSize = 38.sp,
        lineHeight = 32.sp,
    )

    @Composable
    fun normalText1() = textDefault().copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )

    @Composable
    fun scoreText() = textDefault().copy(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold
    )
}
