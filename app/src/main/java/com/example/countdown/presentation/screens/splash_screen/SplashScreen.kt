package com.example.countdown.presentation.screens.splash_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.countdown.R
import com.example.countdown.data.local.datastore.getDayTime
import com.example.countdown.data.local.datastore.getShownBoardingScreen
import com.example.countdown.data.local.datastore.updateTime
import com.example.countdown.presentation.screens.splash_screen.viewmodel.SplashScreenViewModel
import com.example.countdown.presentation.util.Screens
import kotlinx.coroutines.delay
import java.util.*

val colorList1 = listOf(Color(0XFF091B27), Color(0XFFEEF6FF))
val colorList2 = colorList1.reversed()

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val weekData = viewModel.weekDataState

    val date = getDayTime(context = context).collectAsState(initial = Date().toString())

    val navigate = getShownBoardingScreen(context = context).collectAsState(initial = false)

    LaunchedEffect(key1 = true) {
        delay(3000)
        val splitDate = date.value.split(" ")
        val currentSplitDate = Date().toString().split(" ")

        if (splitDate[1] == currentSplitDate[1] && splitDate[2] == currentSplitDate[2]) {
            Toast.makeText(context, "Welcome back", Toast.LENGTH_SHORT).show()
        } else {

            Log.i("basimre", "called")

            if (weekData.value.size < 6) {
                viewModel.getSuccessfulTimer()
            } else {
                viewModel.updateWeekData()
            }
            viewModel.deleteAllDayTimer()
        }

        updateTime(context = context, date = Date())

        if (navigate.value) {
            navHostController.popBackStack()
            navHostController.navigate(Screens.Home.route)
        } else {
            navHostController.popBackStack()
            navHostController.navigate(Screens.OnBoarding1.route)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SplashScreenDesign(colorList = colorList1)
        Image(
            painter = painterResource(id = R.drawable.ic_group_32),
            contentDescription = "Logo",
            modifier = Modifier.size(160.dp)
        )
        SplashScreenDesign(colorList = colorList2)
    }

}

@Composable
fun SplashScreenDesign(
    colorList: List<Color>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(colorList.size) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(color = colorList[it])
            )
        }
    }
}
