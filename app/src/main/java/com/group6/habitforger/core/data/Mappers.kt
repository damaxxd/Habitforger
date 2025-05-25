package com.group6.habitforger.core.data

import com.group6.habitforger.habits.data.database.HabitEntity
import com.group6.habitforger.habits.data.database.HabitStatusEntity
import com.group6.habitforger.habits.domain.Habit
import com.group6.habitforger.habits.domain.HabitStatus
import com.group6.habitforger.tasks.data.database.CategoryEntity
import com.group6.habitforger.tasks.data.database.TaskEntity
import com.group6.habitforger.tasks.domain.Category
import com.group6.habitforger.tasks.domain.Task

fun HabitEntity.toHabit(): Habit {
    return Habit(
        id = id,
        title = title,
        description = description,
        time = time,
        index = index
    )
}

fun HabitStatusEntity.toHabitStatus(): HabitStatus {
    return HabitStatus(
        id = id,
        habitId = habitId,
        date = date
    )
}

fun Habit.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        title = title,
        description = description,
        time = time,
        index = index
    )
}

fun HabitStatus.toHabitStatusEntity(): HabitStatusEntity {
    return HabitStatusEntity(
        id = id,
        habitId = habitId,
        date = date
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        categoryId = categoryId,
        title = title,
        index = index,
        status = status
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        categoryId = categoryId,
        title = title,
        index = index,
        status = status
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        index = index,
        color = color
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        color = color,
        index = index
    )
}