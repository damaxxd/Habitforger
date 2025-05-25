package com.group6.habitforger.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.group6.habitforger.R
import com.group6.habitforger.core.domain.Pages
import com.group6.habitforger.core.presentation.settings.Settings
import com.group6.habitforger.core.presentation.theme.HBTheme
import com.group6.habitforger.habits.presentation.Habits
import com.group6.habitforger.tasks.presentation.Tasks
import com.group6.habitforger.viewmodels.HabitViewModel
import com.group6.habitforger.viewmodels.SettingsViewModel
import com.group6.habitforger.viewmodels.TasksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HB(
    tvm: TasksViewModel = koinViewModel(),
    hvm: HabitViewModel = koinViewModel(),
    svm: SettingsViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    var currentRoute: MainRoutes by remember { mutableStateOf(MainRoutes.TaskPages) }

    val taskPageState by tvm.state.collectAsStateWithLifecycle()
    val habitsPageState by hvm.state.collectAsStateWithLifecycle()
    val settingsState by svm.state.collectAsStateWithLifecycle()

    val navigator = { route: MainRoutes ->
        if (currentRoute != route) {
            navController.navigate(route) {
                launchSingleTop = true
                popUpTo(
                    when (settingsState.startingPage) {
                        Pages.Habits -> MainRoutes.HabitsPages
                        Pages.Tasks -> MainRoutes.TaskPages
                    }
                ) { saveState = true }
                restoreState = true
            }
        }
    }

    HBTheme(
        theme = settingsState.theme
    ) {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                ) {
                    MainRoutes.allRoutes.forEach { route ->
                        NavigationBarItem(
                            selected = currentRoute == route,
                            onClick = {
                                navigator(route)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        when (route) {
                                            MainRoutes.HabitsPages -> R.drawable.round_alarm_24
                                            MainRoutes.TaskPages -> R.drawable.round_checklist_24
                                            else -> R.drawable.round_settings_24
                                        }
                                    ),
                                    contentDescription = null,
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(
                                        when (route) {
                                            MainRoutes.HabitsPages -> R.string.habits
                                            MainRoutes.TaskPages -> R.string.tasks
                                            else -> R.string.settings
                                        }
                                    )
                                )
                            },
                            alwaysShowLabel = false
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = when (settingsState.startingPage) {
                    Pages.Tasks -> MainRoutes.TaskPages
                    Pages.Habits -> MainRoutes.HabitsPages
                },
                modifier = Modifier
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) },
                popEnterTransition = { fadeIn(animationSpec = tween(300)) },
                popExitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                composable<MainRoutes.TaskPages> {
                    currentRoute = MainRoutes.TaskPages

                    Tasks(
                        state = taskPageState,
                        onAction = tvm::taskPageAction
                    )
                }

                composable<MainRoutes.SettingsPages> {
                    currentRoute = MainRoutes.SettingsPages

                    Settings(
                        state = settingsState,
                        onAction = svm::onAction
                    )
                }

                composable<MainRoutes.HabitsPages> {
                    currentRoute = MainRoutes.HabitsPages

                    Habits(
                        state = habitsPageState,
                        onAction = hvm::habitsPageAction
                    )
                }
            }
        }
    }
}