package com.gr6.habitforger.core.domain

import com.gr6.habitforger.habits.domain.Habit

interface AlarmScheduler {
    // Notification scheduler for Habits
    fun schedule(item: Habit)

    // cancel habit notifications
    fun cancel(item: Habit)
}