package com.example.countdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.countdown.presentation.screens.day_detail_screen.DailyDetailScreen
import com.example.countdown.presentation.screens.home_screen.HomeScreen
import com.example.countdown.presentation.screens.onboarding_screen.*
import com.example.countdown.presentation.screens.settings_screen.AccountScreen
import com.example.countdown.presentation.screens.settings_screen.SettingsScreen
import com.example.countdown.presentation.screens.splash_screen.SplashScreen
import com.example.countdown.presentation.screens.week_data_screen.WeekDataScreen
import com.example.countdown.presentation.util.Screens
import com.example.countdown.ui.theme.CountdownTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountdownTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    ApplicationNavigation(navHostController = navController)
                }
            }
        }
    }
}

@Composable
fun ApplicationNavigation(
    navHostController: NavHostController
) {

    NavHost(navController = navHostController, startDestination = Screens.Splash.route) {

        composable(Screens.Splash.route) {
            SplashScreen(navHostController = navHostController)
        }

        composable(Screens.OnBoarding1.route) {
            OnBoardingScreen1(navHostController = navHostController)
        }

        composable(Screens.OnBoardingScreens.route) {
            OnBoardingScreens(navHostController = navHostController)
        }

        composable(Screens.Home.route) {
            HomeScreen(navHostController = navHostController)
        }

        composable(Screens.DayWasted.route) {
            DailyDetailScreen(
                backgroundColor = MaterialTheme.colors.secondary,
                tint = White,
                textColor = White,
                boxColor = MaterialTheme.colors.surface,
                systemBarColor = Color(0XFF091B27),
                content = "wasted",
                navHostController = navHostController
            )
        }
        
        composable(Screens.DayUsed.route) {
            DailyDetailScreen(
                backgroundColor = White,
                tint = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.secondary,
                boxColor = MaterialTheme.colors.secondary,
                systemBarColor = White,
                content = "used",
                navHostController = navHostController
            )
        }

        composable(Screens.WeekDataScreen.route) {
            WeekDataScreen(navHostController = navHostController)
        }

        composable(Screens.Settings.route) {
            SettingsScreen(
                navHostController = navHostController
            )
        }

        composable(Screens.Account.route) {
            AccountScreen(navHostController = navHostController)
        }

    }
}


