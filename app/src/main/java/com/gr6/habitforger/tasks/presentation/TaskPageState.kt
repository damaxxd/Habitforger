package com.gr6.habitforger.tasks.presentation

import androidx.compose.runtime.Immutable
import com.gr6.habitforger.tasks.domain.Category
import com.gr6.habitforger.tasks.domain.Task

// needs parameters for sorted tasks and other things
@Immutable
data class TaskPageState(
    val tasks: Map<Category, List<Task>> = emptyMap(),
    val currentCategory: Category? = null,
    val completedTasks: List<Task> = emptyList()
)
