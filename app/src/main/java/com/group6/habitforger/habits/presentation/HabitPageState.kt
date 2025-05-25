package com.group6.habitforger.habits.presentation

import com.group6.habitforger.habits.domain.Habit
import com.group6.habitforger.habits.domain.HabitStatus
import java.time.DayOfWeek

data class HabitPageState(
    val habitsWithStatuses: Map<Habit, List<HabitStatus>> = emptyMap(),
    val completedHabits: List<Habit> = emptyList(),
    val is24Hr: Boolean = false,
    val startingDay: DayOfWeek = DayOfWeek.MONDAY,
    val analyticsHabitId: Long? = null
)