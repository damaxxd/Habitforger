package com.group6.habitforger.habits.data.repository

import com.group6.habitforger.core.data.toHabit
import com.group6.habitforger.core.data.toHabitEntity
import com.group6.habitforger.core.data.toHabitStatus
import com.group6.habitforger.core.data.toHabitStatusEntity
import com.group6.habitforger.habits.data.database.HabitDao
import com.group6.habitforger.habits.data.database.HabitStatusDao
import com.group6.habitforger.habits.domain.Habit
import com.group6.habitforger.habits.domain.HabitRepo
import com.group6.habitforger.habits.domain.HabitStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class HabitRepository(
    private val habitDao: HabitDao,
    private val habitStatusDao: HabitStatusDao
): HabitRepo {
    override suspend fun upsertHabit(habit: Habit) {
        habitDao.upsertHabit(habit.toHabitEntity())
    }

    override suspend fun deleteHabit(habitId: Long) {
        habitDao.deleteHabit(habitId)
    }

    override suspend fun deleteAllHabits() {
        habitDao.deleteAllHabits()
    }

    override suspend fun getHabits(): List<Habit> {
        return habitDao.getAllHabits().map { it.toHabit() }
    }

    override suspend fun getHabitStatuses(): List<HabitStatus> {
        return habitStatusDao.getHabitStatuses().map { it.toHabitStatus() }
    }

    override fun getHabitStatus(): Flow<Map<Habit, List<HabitStatus>>> {
        val habits = habitDao.getAllHabitsFlow().map { habits ->
            habits.map { it.toHabit() }.sortedBy { it.index }
        }
        val habitStatuses = habitStatusDao
            .getAllHabitStatuses()
            .map { habitStatuses ->
                habitStatuses.map { it.toHabitStatus() }
            }

        return habits.combine(habitStatuses) { habitsFlow, habitStatusesFlow ->
            habitsFlow.associateWith { habit ->
                habitStatusesFlow.filter { it.habitId == habit.id }
            }
        }
    }

    override suspend fun insertHabitStatus(habitStatus: HabitStatus) {
        habitStatusDao.insertHabitStatus(habitStatus.toHabitStatusEntity())
    }

    override suspend fun deleteHabitStatus(id: Long, date: LocalDate) {
        habitStatusDao.deleteStatus(id, date)
    }

    override suspend fun deleteAllHabitStatus() {
        habitStatusDao.deleteAllHabitStatus()
    }
}