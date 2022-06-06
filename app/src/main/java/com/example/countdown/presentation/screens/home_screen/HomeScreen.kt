package com.example.countdown.presentation.screens.home_screen

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.example.countdown.R
import com.example.countdown.common.service.CountDownService
import com.example.countdown.common.util.foregroundStartService
import com.example.countdown.data.local.datastore.getEndTime
import com.example.countdown.data.local.datastore.updateEndTime
import com.example.countdown.domain.model.DayModel
import com.example.countdown.presentation.screens.drawer_screen.DrawerScreen
import com.example.countdown.presentation.screens.home_screen.viewmodel.HomeScreenViewModel
import com.example.countdown.presentation.util.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    val successfulTime = viewModel.successfulTimeState

    val wasteFulTime = viewModel.calculateWastefulTime().collectAsState(initial = Date())

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.White)

    val calender = Calendar.getInstance()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        drawerBackgroundColor = MaterialTheme.colors.primaryVariant,
        drawerContent = {
            DrawerScreen(
                navHostController = navHostController
            )
        },
        scaffoldState = scaffoldState,
        drawerContentColor = MaterialTheme.colors.secondary,
        drawerElevation = 4.dp,
        drawerGesturesEnabled = true
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            HomeTopBar(
                navHostController = navHostController
            ) {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.secondary,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${calculateDay(calender.get(Calendar.DAY_OF_WEEK))},")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(" ${calender.get(Calendar.DAY_OF_MONTH)}th")
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimeBox(
                    background = MaterialTheme.colors.secondary,
                    text = "Successfully Used",
                    time = if (viewModel.dayTimerState.value.isEmpty()) "00 00" else successfulTime.value.toString(),
                    textColor = MaterialTheme.colors.surface
                ) {
                    navHostController.navigate(Screens.DayWasted.route)
                }

                Spacer(modifier = Modifier.width(16.dp))

                TimeBox(
                    background = MaterialTheme.colors.primaryVariant,
                    text = "Successfully Wasted",
                    time =if (viewModel.dayTimerState.value.isEmpty()) "00 00" else  wasteFulTime.value.toString(),
                    textColor = MaterialTheme.colors.primary
                ) {
                    navHostController.navigate(Screens.DayUsed.route)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            QuoteBox()

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                StartButton()
            }
        }
    }
}

@Composable
fun RowScope.TimeBox(
    background: Color,
    text: String,
    time: String,
    textColor: Color,
    onClick: () -> Unit
) {



    Card(
        modifier = Modifier
            .height(120.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
            .weight(.8f)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = background)
            .clickable {
                onClick()
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = text ,
                color = if (text.contains("Wasted")) MaterialTheme.colors.secondary else textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (time.contains(":")) "${time.split(" ")[3].split(":")[0]} : ${time.split(" ")[3].split(":")[1]} Hr" else time,
                color = textColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )

        }

    }
}

@Composable
fun HomeTopBar(
    navHostController: NavHostController,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(onClick = {
            onClick()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                contentDescription = "Navigation drawer icon"
            )
        }


        IconButton(onClick = {
            navHostController.navigate(Screens.Account.route)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                contentDescription = "Account circle"
            )
        }

    }
}

@Composable
fun QuoteBox(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    background: Color = MaterialTheme.colors.surface,
    text: String = "Loading...."
) {

    val isDialogOpen = remember {
        mutableStateOf(false)
    }

    if (isDialogOpen.value) {
        QuoteDialog(
            text = if (viewModel.state.value.isLoading) text else viewModel.state.value.quote?.content ?: "Don't give up!!",
            isDialogOpen = isDialogOpen
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = background)
            .clickable {
                isDialogOpen.value = !isDialogOpen.value
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        
        Box(
            modifier = Modifier.height(100.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Icon(
                painter = painterResource(id = R.drawable.leftquopte),
                contentDescription = "left quote",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(0.6f),
            text = if (viewModel.state.value.isLoading) text else viewModel.state.value.quote?.content ?: "Don't give up!!",
            fontSize = 24.sp,
            fontFamily = FontFamily(
                listOf(Font(R.font.dancing_script))
            ),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .height(100.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_format_quote_24),
                contentDescription = "formatted quote",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .size(32.dp)
            )
        }
        
    }
}

@Composable
fun ButtonDesign(
    size: Dp = 200.dp,
    x: Float
) {

    val radius = with(LocalDensity.current) {
        size.toPx()
    }

    Canvas(
        modifier = Modifier.size(size = size)
    ) {
        drawArc(
            color = Color(0XFF307CBE),
            startAngle = 240f + x,
            sweepAngle = 90f,
            useCenter = false,
            size = Size(width = radius, height = radius),
            style = Stroke(width = 6f, cap = StrokeCap.Round)
        )

        drawArc(
            color = Color(0XFFE8C682),
            startAngle = 120f + x,
            sweepAngle = 90f,
            useCenter = false,
            size = Size(width = radius, height = radius),
            style = Stroke(width = 6f, cap = StrokeCap.Round)
        )

        drawArc(
            color = Color(0XFFED5650),
            startAngle = 360f + x,
            sweepAngle = 90f,
            useCenter = false,
            size = Size(width = radius, height = radius),
            style = Stroke(width = 6f, cap = StrokeCap.Round)
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun StartButton(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var mt by remember {
        mutableStateOf(0)
    }
    var mh by remember {
        mutableStateOf(0)
    }
    var st by remember {
        mutableStateOf(0)
    }
    var sh by remember {
        mutableStateOf(0)
    }
    var hh by remember {
        mutableStateOf(0)
    }
    var ht by remember {
        mutableStateOf(0)
    }

    var time by remember {
        mutableStateOf("00 : 00 : 00")
    }

    var isTimerRunning by remember {
        mutableStateOf(false)
    }

    var starTime by remember {
        mutableStateOf(Date())
    }

    var endTime by remember {
        mutableStateOf(Date())
    }

    var date by remember {
        mutableStateOf(Date())
    }

    val x = animateFloatAsState(
        targetValue = if (isTimerRunning) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            if (intent?.extras != null) {
                val millis =intent.getLongExtra("countdown", 30000)
                val dater = Date(millis)
                val dates = SimpleDateFormat("HH : mm : ss").parse(time)
                val dateo = addDate(dater, dates)
                Log.i("datew", dateo.toString())
            }
        }

    }


    val endDateDataStore = getEndTime(context = context).collectAsState(initial = Date().toString())

    LaunchedEffect(key1 = endDateDataStore.value) {
        endTime = SimpleDateFormat("HH:mm:ss").parse(endDateDataStore.value.split(" ")[3])
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = sh, key2 = isTimerRunning) {

        delay(1000)

        if (isTimerRunning) {
            time = incrementTime(mh, mt, sh, st, hh, ht)
            val a = time.split("")
            ht = a[1].toInt()
            hh = a[2].toInt()
            mt = a[6].toInt()
            mh = a[7].toInt()
            st = a[11].toInt()
            sh = a[12].toInt()

            if (st >= 1) {
                sh = "${a[11]}${a[12]}".toInt()
            }

            if (mt >= 1) {
                mh = "${a[6]}${a[7]}".toInt()
            }

        }
    }

    DisposableEffect(key1 = lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_START) {

            } else if (event == Lifecycle.Event.ON_STOP) {

                try {
                    context.unregisterReceiver(broadcastReceiver)
                } catch (e: Exception) {

                }

            }else if (event == Lifecycle.Event.ON_RESUME) {
                context.registerReceiver(broadcastReceiver, IntentFilter(CountDownService.COUNTDOWN_BR))
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                context.unregisterReceiver(broadcastReceiver)
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                context.stopService(Intent(context, CountDownService::class.java))
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {

        ButtonDesign(
            x = x.value
        )

        Button(
            onClick = {

                calculateDifferenceBetweenTime(Date(), Date())

                if (!isTimerRunning) {
                    context.foregroundStartService("Start")
                    starTime = Calendar.getInstance().time

                    val dayModel = DayModel(
                        start_time = endTime,
                        end_time = starTime,
                        time = calculateDifferenceBetweenTime(starTime, endTime),
                        time_indicator = 0,
                        date = Date()
                    )

                    viewModel.insertDayTimer(dayModel = dayModel)

                } else {
                    context.foregroundStartService("Stop")
                    date = SimpleDateFormat("HH : mm : ss").parse(time) as Date
                    endTime = Calendar.getInstance().time

                    scope.launch {
                        updateEndTime(context = context, endTime = endTime)
                    }

                    val dayModel = DayModel(
                        start_time = starTime,
                        end_time = endTime,
                        time = date,
                        time_indicator = 1,
                        date = Date()
                    )

                    viewModel.insertDayTimer(dayModel = dayModel)

                }

                isTimerRunning = !isTimerRunning
                if (!isTimerRunning) {
                    mt = 0
                    mh = 0
                    st = 0
                    sh = 0
                    time = "00 : 00"
                }
            },
            modifier = Modifier
                .size(170.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .clip(shape = CircleShape),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            )
        ) {
            Text(
                text = if (isTimerRunning) time else stringResource(id = R.string.ready),
                color = MaterialTheme.colors.primary,
                fontSize = 24.sp
            )
        }

    }
}

@Composable
fun QuoteDialog(
    text: String,
    isDialogOpen: MutableState<Boolean>
) {

    if (isDialogOpen.value) {

        Dialog(onDismissRequest = {
            isDialogOpen.value = false
        }) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                       isDialogOpen.value = false
                    },
                contentAlignment = Alignment.Center
            ) {
                QuoteBox(
                    modifier = Modifier
                        .height(300.dp),
                    text = text
                )
            }
        }

    }
}