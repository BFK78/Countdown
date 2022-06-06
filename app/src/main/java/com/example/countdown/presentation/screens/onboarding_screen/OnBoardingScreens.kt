package com.example.countdown.presentation.screens.onboarding_screen

import android.util.Log
import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.countdown.R
import com.example.countdown.data.local.datastore.getShownBoardingScreen
import com.example.countdown.data.local.datastore.updateShownBoardingScreen
import com.example.countdown.domain.model.OnBoardingScreenModel
import com.example.countdown.domain.model.UserModel
import com.example.countdown.presentation.screens.onboarding_screen.viewmodel.OnBoardingViewModel
import com.example.countdown.presentation.util.Screens
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalPagerApi::class)
val screenList = listOf(
    OnBoardingScreenModel(
        question = R.string.question2,
        action = { pager, nav, onSave, onChange ->
            Action2(pagerState = pager, onChange)
        }
    ),
    OnBoardingScreenModel(
        question = R.string.question3,
        action = { pager, nav,onSave, onChange ->
            Action3(pagerState = pager, nav,"wakeup", onSave , onChange)
        }
    ),
    OnBoardingScreenModel(
        question = R.string.question4,
        action = { pager, nav, onSave, onChange ->
            Action3(pagerState = pager, nav,"sleep", onSave, onChange)
        }
    )
)



@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreens(
    navHostController: NavHostController,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {

    val pagerState = rememberPagerState()

    var name by remember {
        mutableStateOf("")
    }

    var hWakeup by remember {
        mutableStateOf("4")
    }

    var mWakeup by remember {
        mutableStateOf("30")
    }

    var hSleep by remember {
        mutableStateOf("4")
    }

    var mSleep by remember {
        mutableStateOf("30")
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        HorizontalPager(count = screenList.size, state = pagerState) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {

                TriangleDesign()

                TriangleDesignBottom()

                OnBoardingContent(
                    question = stringResource(id = screenList[it].question)
                ) {
                    Column(
                    ) {
                        screenList[it].action(this, pagerState, navHostController, {

                            Log.i("basim", "$hWakeup:$mWakeup")

                            val wakeTime = "$hWakeup:$mWakeup"
                            val wDate = SimpleDateFormat("hh:mm").parse(wakeTime)
                            val sleepTime = "$hSleep:$mSleep"
                            val sDate = SimpleDateFormat("hh:mm").parse(sleepTime)


                            val user = UserModel(
                                name = name,
                                wake_up = wDate,
                                go_down = sDate
                            )
                            viewModel.insertUser(user = user)

                        }) { a, b ->


                            when(b) {
                                "name" -> {
                                    name = a
                                }
                                "Hour wakeup" -> {
                                    hWakeup = a
                                }
                                "Minutes wakeup" -> {
                                    mWakeup = a
                                }
                                "Hour sleep" -> {
                                    hSleep = a
                                }
                                "Minutes sleep" -> {
                                    mSleep = a
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun OnBoardingScreen1(
    navHostController: NavHostController
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        updateShownBoardingScreen(context = context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TriangleDesign()
        TriangleDesignBottom()
        
        OnBoardingContent(
            question = stringResource(id = R.string.question1)
        ) {
            Action1() {
                navHostController.popBackStack()
                navHostController.navigate(Screens.OnBoardingScreens.route)
            }
        }

    }
}
//
//@Composable
//fun OnBoardingScreen2(
//    navHostController: NavHostController,
//    viewModel: OnBoardingViewModel = hiltViewModel()
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        TriangleDesign()
//        TriangleDesignBottom()
//
//        OnBoardingContent(
//            question = stringResource(id = R.string.question2)
//        ) {
//
//        }
//
//    }
//}
//
//@Composable
//fun OnBoardingScreen3(
//    navHostController: NavHostController
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        TriangleDesign()
//        TriangleDesignBottom()
//
//        OnBoardingContent(
//            question = stringResource(id = R.string.question3)
//        ) {
//        }
//
//    }
//}
//
//@Composable
//fun OnBoardingScreen4(
//    navHostController: NavHostController
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        TriangleDesign()
//
//        TriangleDesignBottom()
//
//        OnBoardingContent(
//            question = stringResource(id = R.string.question4)
//        ) {
//
//        }
//
//    }
//}

@Composable
fun BoxScope.TriangleDesign() {

    val density = LocalDensity.current.density

    val path = Path().apply {
        moveTo(x = 0f, y = 0f)
        lineTo(x = 100 * density, y = 0f)
        lineTo(x = 0f , 100f * density)
    }

    Canvas(
        modifier = Modifier
            .align(alignment = Alignment.TopStart)
            .size(100.dp)
    ) {
        drawPath(path = path, color = Color(0XFFEEF6FF))
    }
}

@Composable
fun BoxScope.TriangleDesignBottom() {
    val density = LocalDensity.current.density

    val path = Path().apply {
        moveTo(x = 100f * density, 100f * density)
        lineTo(100f * density, 0f)
        lineTo(0f, 100f * density)
    }

    Canvas(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .size(100.dp)
    ) {
        drawPath(path = path, Color(0XFF091B27))
    }
}

@Composable
fun BoxScope.OnBoardingContent(
    question: String,
    action: @Composable () -> Unit
) {

    Column(
        modifier = Modifier
            .align(Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = question,
            style = TextStyle(
                fontFamily = FontFamily(
                    listOf(Font(R.font.dancing_script))
                )
            ),
            fontSize = 32.sp
        )
        action()
    }
}

@Composable
fun Action1(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

    Button(
        modifier = Modifier.width(width = 140.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant
        )
    ) {
        Text(
            text = stringResource(id = R.string.yes),
            color = MaterialTheme.colors.secondary
        )
    }

        Spacer(modifier = Modifier.width(16.dp))

       Button(
           modifier = Modifier.width(width = 140.dp),
           onClick = {  },
           colors = ButtonDefaults.buttonColors(
               backgroundColor = MaterialTheme.colors.secondary,
           ),
       ) {
           Text(
               text = stringResource(id = R.string.no),
               color = MaterialTheme.colors.primary
           )
       }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Action2(
    pagerState: PagerState,
    onChange: (String, String) -> Unit
) {

    val scope = rememberCoroutineScope()
    
    var error by  remember {
        mutableStateOf(false)
    }
    
    val alpha = animateFloatAsState(targetValue = if (error) 1f else 0f, animationSpec = tween(200))
    
    val color = animateColorAsState(targetValue = if (error) MaterialTheme.colors.error else Color.Transparent)

    var name by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            TextField(
                value = name,
                onValueChange = {
                name = it
                onChange(name, "name")
            },
                modifier = Modifier.border(width = .5.dp, color = color.value),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.secondary,
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    focusedLabelColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontFamily = FontFamily(listOf(Font(R.font.dancing_script))),
                    fontSize = 20.sp
                ),
                label = {
                    Text(
                        text = "Enter your name",
                        fontFamily = FontFamily(Font(R.font.dancing_script)),
                        fontSize = 20.sp
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Error info",
                            modifier = Modifier.alpha(alpha.value),
                            tint = color.value
                        )
                    }
                }
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .background(color = MaterialTheme.colors.secondary)

        ) {
            IconButton(onClick =  {
                if (name.isBlank()) {
                    error = true
                } else {
                    error = false
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )
                    }
                }

            } ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Next Button",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Action3(
    pagerState: PagerState,
    navHostController: NavHostController,
    label: String,
    onSave: () -> Unit,
    onChange: (String, String) -> Unit
) {

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TimeBox(
                background = MaterialTheme.colors.secondary,
                tint = MaterialTheme.colors.primaryVariant,
                textColor = MaterialTheme.colors.primaryVariant,
                label = "Hour $label",
                onChange = onChange
            )

            TimeBox(
                background = MaterialTheme.colors.primaryVariant,
                tint = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.secondary,
                label = "Minutes $label",
                onChange = onChange
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .background(color = MaterialTheme.colors.secondary)

        ) {
            IconButton(onClick = {
                scope.launch {
                    if (pagerState.currentPage != 2) {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )
                    } else {
                        onSave()
                        navHostController.popBackStack()
                        navHostController.navigate(Screens.Home.route)
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Next Button",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun TimeBox(
    background: Color,
    tint: Color,
    textColor: Color,
    label: String,
    height: Dp = 180.dp,
    width: Dp = 150.dp,
    textSize: TextUnit = 32.sp,
    iconSize: Dp = 48.dp,
    hour: Int = 4,
    minute: Int = 30,
    edit: Boolean = true,
    onChange: (String, String) -> Unit
) {

    var time by remember {
        mutableStateOf(if (label.contains("Hour")) hour else minute)
    }

    Card(
        modifier = Modifier
            .size(width = width, height = height)
            .clip(RoundedCornerShape(10.dp))
            .background(color = background)

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(onClick = {
//                if (time < 24) {
//                    time++
//                    onChange(time.toString(), label)
//                }

                if (edit) {

                if (label.contains("Hour")) {
                    if (time < 23) {
                        time ++
                        onChange(time.toString(), label)
                    }
                } else {
                    if (time < 59) {
                        time ++
                        onChange(time.toString(), label)
                    }
                }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Increase time",
                    tint = tint,
                    modifier = Modifier.size(iconSize)
                )
            }

            Text(
                text = time.toString(),
                color = textColor,
                fontSize = textSize
            )

            IconButton(onClick = {
                if (edit) {
                if (time > 0) {
                    time--
                    onChange(time.toString(), label)
                }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrease the time",
                    tint = tint,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}