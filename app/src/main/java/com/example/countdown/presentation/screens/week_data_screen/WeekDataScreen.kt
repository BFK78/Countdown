package com.example.countdown.presentation.screens.week_data_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.countdown.R
import com.example.countdown.domain.model.WeekModel
import com.example.countdown.presentation.screens.day_detail_screen.viewmodel.DailyDetailViewModel
import com.example.countdown.presentation.screens.week_data_screen.viewmodel.WeekDataScreenViewModel
import com.example.countdown.presentation.util.calculateTotalMinutes
import com.example.countdown.presentation.util.createDate
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import java.util.*


val listOfHours = listOf<String>(
    "24",
    "20",
    "15",
    "10",
    "5",
    "0"
)

val sampleList = listOf(
    WeekModel(successful_time = createDate(10,23), unsuccessful_time = createDate(13,43), day = "Mon", date = Date()),
    WeekModel(successful_time = createDate(8,43), unsuccessful_time = createDate(2,2), day = "Tue", date = Date()),
    WeekModel(successful_time = createDate(3,13), unsuccessful_time = createDate(3,34), day = "Wen", date = Date()),
    WeekModel(successful_time = createDate(5,43), unsuccessful_time = createDate(21,5), day = "Thu", date = Date()),
    WeekModel(successful_time = createDate(18,3), unsuccessful_time = createDate(6,6), day = "Fri", date = Date()),
    WeekModel(successful_time = createDate(1,23), unsuccessful_time = createDate(12,32), day = "Sat", date = Date()),
    WeekModel(successful_time = createDate(7,20), unsuccessful_time = createDate(22,5), day = "Sun", date = Date()),
)

@Composable
fun WeekDataScreen(
    navHostController: NavHostController,
    viewModel: WeekDataScreenViewModel = hiltViewModel(),
    dayViewModel: DailyDetailViewModel = hiltViewModel()
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.White)

    val weekData = viewModel.weekDataState

    var successfulDay = Date()

    var totalSuccessfulMinutes = 0f

    var totalWastefulMinutes = 0f

    var successfulTime = Date()

    var wastefulTime = Date()

    var wasteDay = Date()

    weekData.value.forEach {
        val successfulTotalMinutes = calculateTotalMinutes(it.successful_time)
        if (totalSuccessfulMinutes < successfulTotalMinutes) {
            totalSuccessfulMinutes = successfulTotalMinutes
            successfulTime = it.successful_time
            successfulDay = it.date
        }
        val wastefulTotalMinutes = calculateTotalMinutes(it.unsuccessful_time)
        if (totalWastefulMinutes < wastefulTotalMinutes) {
            totalWastefulMinutes = wastefulTotalMinutes
            wastefulTime = it.unsuccessful_time
            wasteDay = it.date
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        IconButton(
            onClick = {
            navHostController.popBackStack()
        },
        modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow back"
            )
        }

        if (weekData.value.isNotEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 48.dp, horizontal = 24.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Week Data",
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 32.sp,
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.height(48.dp))

                if (weekData.value.isNotEmpty()) {
                    LineChartView(
                        list = weekData.value
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                WeekStatus(
                    status = "Successful",
                    date = successfulDay.toString().split(" ")[0] + " " + successfulDay.toString()
                        .split(" ")[1] + " " + successfulDay.toString().split(" ")[2],
                    hour = (totalSuccessfulMinutes / 60).toString(),
                    percentage = dayViewModel.calculateTimePercentage(
                        totalTime = dayViewModel.totalTimeDateState.value,
                        successfulTime
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                WeekStatus(
                    status = "Wasteful",
                    date = wasteDay.toString().split(" ")[0] + " " + wasteDay.toString()
                        .split(" ")[1] + " " + wasteDay.toString().split(" ")[2],
                    hour = (totalWastefulMinutes / 60).toString(),
                    percentage = dayViewModel.calculateTimePercentage(
                        totalTime = dayViewModel.totalTimeDateState.value,
                        wastefulTime
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Do More!",
                    fontFamily = FontFamily(
                        listOf(
                            Font(R.font.dancing_script)
                        )
                    ),
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp
                )
            }
        } else {

            Text(
                text = "We need more data to show your weekly progress.",
                modifier = Modifier.align(Alignment.Center)
            )

        }
    }
}

@Composable
fun WeekStatus(
    status: String,
    date: String,
    hour: String,
    percentage: String
) {
    Column {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.secondary,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Most ")
                }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                ) {
                    append(status)
                }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.secondary,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(" day in the week")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 14.sp
                    )
                ) {
                    append("On $date you have ${if(status == "Successful") "used" else "wasted"} $hour hour\ni,e; $percentage% of your Day!")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LineChartView(
    list: List<WeekModel>
) {

    val pointList = mutableListOf<LineChartData.Point>()


    Log.i("listing the w", list.toString())

    list.forEach {
        pointList.add(LineChartData.Point(calculateTotalMinutes(it.successful_time), it.day))
    }

    Log.i("pointList", pointList.toString())

    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {

        LineChart(
            lineChartData = LineChartData(
                points = pointList
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            animation = simpleChartAnimation(),
            pointDrawer = FilledCircularPointDrawer(color = MaterialTheme.colors.primary),
            lineDrawer = SolidLineDrawer(color = MaterialTheme.colors.secondary, thickness = 1.dp),
            xAxisDrawer = SimpleXAxisDrawer(),
            yAxisDrawer = SimpleYAxisDrawer(),
            horizontalOffset = 5f
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
                .graphicsLayer {
                    translationX = -60f
                    translationY = -60f
                }
                .align(alignment = Alignment.TopStart)

                .background(color = Color.White)

        ) {

        }
    }
}