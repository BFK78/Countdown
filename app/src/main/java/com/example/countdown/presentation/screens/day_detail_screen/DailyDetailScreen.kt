package com.example.countdown.presentation.screens.day_detail_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.countdown.R
import com.example.countdown.domain.model.DayModel
import com.example.countdown.domain.model.TimeIntervalModel
import com.example.countdown.presentation.screens.day_detail_screen.viewmodel.DailyDetailViewModel
import com.example.countdown.presentation.screens.home_screen.viewmodel.HomeScreenViewModel
import com.example.countdown.presentation.util.Screens
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.*


val sampleData = listOf(
    TimeIntervalModel("7:30am-8:30am", "1:03 hr"),
    TimeIntervalModel("7:30am-8:30am", "1:03 hr"),
    TimeIntervalModel("7:30am-8:30am", "1:03 hr"),
    TimeIntervalModel("7:30am-8:30am", "1:03 hr"),
    TimeIntervalModel("7:30am-8:30am", "1:03 hr"),
    TimeIntervalModel("7:30am-8:30am", "1:03 hr"),
    TimeIntervalModel("7:30am-8:30am", "1:03 hr"),
    TimeIntervalModel("7:30am-8:30am", "1:03 hr")
)

@Composable
fun DailyDetailScreen(
    backgroundColor: Color,
    tint: Color,
    textColor: Color,
    boxColor: Color,
    viewModel: DailyDetailViewModel = hiltViewModel(),
    systemBarColor: Color,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    content: String,
    navHostController: NavHostController
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = systemBarColor)

    val nameState = viewModel.state

    val successfulDayList = viewModel.successfulTimerState

    val wastedDayList = viewModel.wastefulTimerState

    val totalSuccessfulTime = homeScreenViewModel.successfulTimeState

    val totalTime = viewModel.totalTimeState

    val totalWastedTime = homeScreenViewModel.calculateWastefulTime().collectAsState(initial = Date())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow back",
                    tint = tint,
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = -24f
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 32.sp,
                        fontFamily = FontFamily(
                            listOf(
                                Font(
                                    R.font.dancing_script
                                )
                            )
                        )
                    )
                ) {
                    append("Hey, ")
                }

                withStyle(
                    style = SpanStyle(
                        color = textColor,
                        fontSize = 32.sp,
                        fontFamily = FontFamily(
                            listOf(
                                Font(
                                    R.font.dancing_script
                                )
                            )
                        )
                    )
                ) {
                    append(nameState.value?.name ?: "")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = textColor
                    )
                ) {
                    append("You've Successfully $content ")
                }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 24.sp
                    )
                ) {
                    append(if (content == "used")totalSuccessfulTime.value.toString().split(" ")[3].split(":")[0] else totalWastedTime.value.toString().split(" ")[3].split(":")[0])
                }

                withStyle(
                    style = SpanStyle(
                        color = textColor
                    )
                ) {
                    append(" hours and ")
                }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 24.sp
                    )
                ) {
                    append(if (content == "used") "${totalSuccessfulTime.value.toString().split(" ")[3].split(":")[1]}\n" else "${totalWastedTime.value.toString().split(" ")[3].split(":")[1]}\n")
                }

                withStyle(
                    style = SpanStyle(
                        color = textColor
                    )
                ) {
                    append("minutes of your total ")
                }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 24.sp
                    )
                ) {
                    append(totalTime.value)
                }

                withStyle(
                    style = SpanStyle(
                        color = textColor
                    )
                ) {
                    append(" hours")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        TimeIntervals(
            backgroundColor = boxColor,
            title = "${content.capitalize(Locale.current)} Time Intervals",
            timeIntervals = if (content == "used") successfulDayList.value else wastedDayList.value,
            textColor = textColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = textColor
                    )
                ) {
                    append("You have successfully $content ")
                }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 32.sp
                    )
                ) {
                    append(viewModel.calculateTimePercentage(viewModel.totalTimeDateState.value, if (content == "used") totalSuccessfulTime.value else totalWastedTime.value))
                }

                withStyle(
                    style = SpanStyle(
                        color = textColor
                    )
                ) {
                    append(" of your time today")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "compare with more data",
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                navHostController.navigate(Screens.WeekDataScreen.route)
            }
        )
    }
}

@Composable
fun TimeIntervals(
    backgroundColor: Color,
    title: String,
    timeIntervals: List<DayModel>,
    textColor: Color
) {

    val verticalScroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth(.8f)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = backgroundColor.copy(alpha = 0.2f))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Column(
            modifier = Modifier
                .height(height = 260.dp)
                .verticalScroll(state = verticalScroll)
        ) {
            repeat(timeIntervals.size) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${
                            timeIntervals[it].start_time.toString().split(" ")[3].split(":")[0]
                        } : ${
                            timeIntervals[it].start_time.toString().split(" ")[3].split(":")[1]
                        } - ${
                            timeIntervals[it].end_time.toString().split(" ")[3].split(":")[0]
                        } : ${
                            timeIntervals[it].end_time.toString().split(" ")[3].split(":")[1]
                        } hr",
                        color = textColor
                    )

                    Text(
                        text = timeIntervals[it].time.toString().split(" ")[3],
                        color = textColor
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}