package com.group6.habitforger.core.presentation.settings.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.group6.habitforger.R
import com.group6.habitforger.core.domain.backup.ExportState
import com.group6.habitforger.core.domain.backup.RestoreState
import com.group6.habitforger.core.presentation.components.PageFill
import com.group6.habitforger.core.presentation.settings.SettingsAction
import com.group6.habitforger.core.presentation.settings.SettingsState
import com.group6.habitforger.core.presentation.theme.HBTheme

import com.group6.habitforger.core.presentation.theme.HBTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Backup(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    onNavigateBack: () -> Unit
) = PageFill {
    LaunchedEffect(Unit) {
        onAction(SettingsAction.OnResetBackupState)
    }

    var uri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri = it }

    Column(
        modifier = Modifier
            .widthIn(max = 500.dp)
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.backup)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onNavigateBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Navigate Back"
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.export)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = stringResource(R.string.export_desc)
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { onAction(SettingsAction.OnExport) },
                            enabled = state.backupState.exportState == ExportState.IDLE
                        ) {
                            when (state.backupState.exportState) {
                                ExportState.IDLE -> Icon(
                                    painter = painterResource(R.drawable.round_play_arrow_24),
                                    contentDescription = "Start"
                                )

                                ExportState.EXPORTING -> CircularProgressIndicator(
                                    modifier = Modifier.size(
                                        24.dp
                                    )
                                )

                                ExportState.EXPORTED -> Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Done"
                                )
                            }
                        }
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.restore)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = stringResource(R.string.restore_desc)
                        )
                    },
                    trailingContent = {
                        if (uri == null) {
                            TextButton(
                                onClick = { launcher.launch(arrayOf("application/json")) }
                            ) {
                                Text(
                                    text = stringResource(R.string.choose_file)
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { onAction(SettingsAction.OnRestore(uri!!)) },
                                enabled = state.backupState.restoreState == RestoreState.IDLE
                            ) {
                                when (state.backupState.restoreState) {
                                    RestoreState.IDLE -> Icon(
                                        painter = painterResource(R.drawable.round_play_arrow_24),
                                        contentDescription = "Start"
                                    )

                                    RestoreState.RESTORING -> CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp)
                                    )

                                    RestoreState.RESTORED -> Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Done"
                                    )

                                    RestoreState.FAILURE -> Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "Fail"
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun BackupPreview() {
    HBTheme {
        Backup(
            state = SettingsState(), // hoặc giá trị mặc định phù hợp với app của bạn
            onAction = {},
            onNavigateBack = {}
        )
    }
}