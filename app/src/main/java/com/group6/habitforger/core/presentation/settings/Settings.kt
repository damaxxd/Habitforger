package com.group6.habitforger.core.presentation.settings

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.group6.habitforger.core.presentation.components.PageFill
import com.group6.habitforger.core.presentation.settings.components.AboutPage
import com.group6.habitforger.core.presentation.settings.components.Backup
import com.group6.habitforger.core.presentation.settings.components.ThemePage
import com.group6.habitforger.core.presentation.settings.components.RootPage
import com.group6.habitforger.core.presentation.theme.HBTheme

@Composable
fun Settings(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) = PageFill {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SettingsRoutes.Root,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }) + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        }
    ) {
        composable<SettingsRoutes.Root> {
            RootPage(
                state = state,
                onAction = onAction,
                onNavigate = { navController.navigate(it) }
            )
        }

        composable<SettingsRoutes.About> {
            AboutPage(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable<SettingsRoutes.LookAndFeel> {
            ThemePage(
                state = state,
                onAction = onAction,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable<SettingsRoutes.Backup> {
            Backup(
                state = state,
                onAction = onAction,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun Preview() {
    HBTheme {
        Settings(
            state = SettingsState(),
            onAction = {}
        )
    }
}