package com.group6.habitforger.core.domain

import androidx.annotation.StringRes
import com.group6.habitforger.R

enum class AppTheme(
    @StringRes val fullName: Int
) {
    SYSTEM(R.string.system),
    DARK(R.string.dark),
    LIGHT(R.string.light)
}