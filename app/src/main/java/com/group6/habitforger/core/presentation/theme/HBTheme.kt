package com.group6.habitforger.core.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.materialkolor.DynamicMaterialTheme
import com.group6.habitforger.core.domain.AppTheme

@Composable
fun HBTheme(
    theme: Theme = Theme(),
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = if (theme.isMaterialYou && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            colorResource(android.R.color.system_accent1_200)
        } else {
            theme.seedColor
        },
        useDarkTheme = when (theme.appTheme) {
            AppTheme.SYSTEM -> isSystemInDarkTheme()
            AppTheme.DARK -> true
            AppTheme.LIGHT -> false
        },
        withAmoled = theme.isAmoled,
        style = theme.paletteStyle,
        typography = provideTypography(1f),
        content = content
    )
}