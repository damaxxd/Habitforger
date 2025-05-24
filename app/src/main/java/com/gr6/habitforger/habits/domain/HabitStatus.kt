package com.gr6.habitforger.habits.domain

import java.time.LocalDate

data class HabitStatus(
    val id: Long = 0,
    val habitId: Long,
    val date: LocalDate
)
