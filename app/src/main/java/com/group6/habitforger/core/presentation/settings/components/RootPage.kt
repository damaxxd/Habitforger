package com.group6.habitforger.core.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.group6.habitforger.R
import com.group6.habitforger.core.domain.Pages
import com.group6.habitforger.core.presentation.components.PageFill
import com.group6.habitforger.core.presentation.settings.SettingsAction
import com.group6.habitforger.core.presentation.settings.SettingsRoutes
import com.group6.habitforger.core.presentation.settings.SettingsState
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootPage(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    onNavigate: (SettingsRoutes) -> Unit
) = PageFill {

    Column(
        modifier = Modifier
            .widthIn(max = 500.dp)
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.settings)
                )
            }
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.pause_notifications)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = stringResource(R.string.pause_notifications_desc)
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = state.pauseNotifications,
                            onCheckedChange = {
                                onAction(SettingsAction.ChangePauseNotifications(it))
                            }
                        )
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.show_habits)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = stringResource(R.string.show_habits_desc)
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = state.startingPage == Pages.Habits,
                            onCheckedChange = {
                                onAction(SettingsAction.ChangeStartingPage(
                                    if (it) Pages.Habits else Pages.Tasks
                                ))
                            }
                        )
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.staring_day)
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = state.startOfTheWeek == DayOfWeek.SUNDAY,
                            onCheckedChange = {
                                onAction(
                                    SettingsAction.ChangeStartOfTheWeek(if (it) DayOfWeek.SUNDAY else DayOfWeek.MONDAY)
                                )
                            }
                        )
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.use_24Hr)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = stringResource(R.string.use_24Hr_desc)
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = state.is24Hr,
                            onCheckedChange = {
                                onAction(SettingsAction.ChangeIs24Hr(it))
                            }
                        )
                    }
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(32.dp)
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.theme)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = stringResource(R.string.theme_desc)
                        )
                    },
                    trailingContent = {
                        FilledTonalIconButton(
                            onClick = { onNavigate(SettingsRoutes.LookAndFeel) }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.backup)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = stringResource(R.string.backup_desc)
                        )
                    },
                    trailingContent = {
                        FilledTonalIconButton(
                            onClick = { onNavigate(SettingsRoutes.Backup) }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.about)
                        )
                    },
                    trailingContent = {
                        FilledTonalIconButton(
                            onClick = { onNavigate(SettingsRoutes.About) }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }
}