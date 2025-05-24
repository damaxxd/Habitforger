package com.gr6.habitforger.core.presentation.theme

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.gr6.habitforger.core.domain.AppTheme

data class Theme(
    val appTheme: AppTheme = AppTheme.SYSTEM,
    val isAmoled: Boolean = false,
    val isMaterialYou: Boolean = false,
    val paletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
    val seedColor: Color = Color.White
)