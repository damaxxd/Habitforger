package com.group6.habitforger.core.domain

import com.group6.habitforger.habits.domain.Habit

interface AlarmScheduler {
    // Notification scheduler for Habits
    fun schedule(habit: Habit)

    // cancel habit notifications
    fun cancel(habit: Habit)

    fun cancelAll()
}