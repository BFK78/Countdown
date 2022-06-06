package com.example.countdown.presentation.util

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.countdown.R

sealed class Screens(val title: String, val route: String, val icon: Int? = null) {
    object Account: Screens("Account", "account", R.drawable.ic_baseline_account_circle_24)
    object Settings: Screens("Settings", "settings", R.drawable.ic_baseline_settings_24)
    object Splash: Screens("SplashScreen", "splash_screen")
    object OnBoarding1: Screens("OnBoarding1", "onboarding_screen1")
//    object OnBoarding2: Screens("OnBoarding2", "onboarding_screen2")
//    object OnBoarding3: Screens("OnBoarding3", "onboarding_screen3")
//    object OnBoarding4: Screens("OnBoarding4", "onboarding_screen4")
    object OnBoardingScreens: Screens("OnBoardingScreens", "onboarding_screens")
    object Home: Screens("Home", "home_screen")
    object DayWasted: Screens("DayWasted", "day_wasted")
    object DayUsed: Screens("DayUsed", "day_used")
    object WeekDataScreen: Screens(title = "WeekDataScreen", route = "week_data_screen")
}
