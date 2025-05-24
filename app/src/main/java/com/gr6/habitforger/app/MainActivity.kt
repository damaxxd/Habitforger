package com.gr6.habitforger.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gr6.habitforger.core.presentation.createNotificationChannel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        createNotificationChannel(this)

        enableEdgeToEdge()

        setContent {
            // Initialising KoinContext, because of warnings
            KoinContext {
                Grit()
            }
        }
    }
}