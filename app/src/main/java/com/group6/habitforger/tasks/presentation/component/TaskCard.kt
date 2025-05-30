package com.group6.habitforger.tasks.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.group6.habitforger.R
import com.group6.habitforger.core.presentation.components.HBDialog
import com.group6.habitforger.tasks.domain.Task

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    task: Task,
    onStatusChange: (Task) -> Unit,
    dragState: Boolean = false,
    reorderIcon: @Composable () -> Unit,
    modifier: Modifier
) {
    var taskStatus by remember { mutableStateOf(task.status) }
    var showEditDialog by remember { mutableStateOf(false) }

    val cardContent by animateColorAsState(
        targetValue = when (taskStatus) {
            true -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.secondary
        },
        label = "cardContent"
    )
    val cardContainer by animateColorAsState(
        targetValue = when (taskStatus) {
            true -> MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.surfaceContainerHighest
        },
        label = "cardContainer"
    )
    val cardColors = CardDefaults.cardColors(
        containerColor = cardContainer,
        contentColor = cardContent
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .combinedClickable(
                // change status on click
                onClick = {
                    if (!dragState) {
                        taskStatus = !taskStatus
                        task.status = !task.status
                        onStatusChange(task)
                    }
                },
                // edit on click and hold
                onLongClick = {
                    if (!dragState) {
                        showEditDialog = true
                    }
                }
            ),
        colors = cardColors,
        shape = MaterialTheme.shapes.large,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textDecoration = if (taskStatus) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                },
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            AnimatedVisibility(
                visible = dragState
            ) {
                reorderIcon()
            }
        }
    }

    // edit dialog
    if (showEditDialog) {
        var newTitle by remember { mutableStateOf(task.title) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }

        HBDialog(
            onDismissRequest = { showEditDialog = false }
        ) {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.None
                ),
                keyboardActions = KeyboardActions(
                    onAny = {
                        newTitle.plus("\n")
                    }
                ),
                modifier = Modifier.focusRequester(focusRequester),
                label = { Text(text = stringResource(id = R.string.edit_task)) }
            )

            Button(
                onClick = {
                    task.title = newTitle
                    onStatusChange(task)
                    showEditDialog = false
                },
                enabled = newTitle.isNotBlank() && newTitle.length <= 100
            ) {
                Text(stringResource(R.string.edit_done))
            }
        }
    }
}