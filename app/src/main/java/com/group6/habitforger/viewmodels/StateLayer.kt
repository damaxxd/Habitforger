package com.group6.habitforger.viewmodels

import com.group6.habitforger.core.presentation.settings.SettingsState
import com.group6.habitforger.habits.presentation.HabitPageState
import com.group6.habitforger.tasks.presentation.TaskPageState
import kotlinx.coroutines.flow.MutableStateFlow

class StateLayer {
    val tasksState = MutableStateFlow(TaskPageState())
    val habitsState = MutableStateFlow(HabitPageState())
    val settingsState = MutableStateFlow(SettingsState())
}