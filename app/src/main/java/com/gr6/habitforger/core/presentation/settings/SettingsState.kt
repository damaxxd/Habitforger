package com.gr6.habitforger.core.presentation.settings

import com.gr6.habitforger.core.domain.Pages
import com.gr6.habitforger.core.domain.backup.ExportState
import com.gr6.habitforger.core.domain.backup.RestoreState
import com.gr6.habitforger.core.presentation.theme.Theme
import java.time.DayOfWeek

data class SettingsState(
    val theme: Theme = Theme(),
    val is24Hr: Boolean = false,
    val startOfTheWeek: DayOfWeek = DayOfWeek.MONDAY,
    val pauseNotifications: Boolean = false,
    val startingPage: Pages = Pages.Tasks,
    val backupState: BackupState = BackupState()
)

data class BackupState(
    val exportState: ExportState = ExportState.IDLE,
    val restoreState: RestoreState = RestoreState.IDLE
)