package com.gr6.habitforger.habits.presentation

import kotlinx.serialization.Serializable

sealed interface HabitRoutes {
    @Serializable
    data object HabitList : HabitRoutes

    @Serializable
    data object HabitAnalytics : HabitRoutes
}