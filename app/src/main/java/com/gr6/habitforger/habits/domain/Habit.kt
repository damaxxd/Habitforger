package com.gr6.habitforger.habits.domain

import java.time.LocalDateTime

data class Habit(
    val id: Long = 0,
    val title: String,
    val description: String,
    val time: LocalDateTime,
    val index: Int
)
