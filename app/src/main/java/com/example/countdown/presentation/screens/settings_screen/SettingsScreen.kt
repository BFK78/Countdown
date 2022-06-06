package com.example.countdown.presentation.screens.settings_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.countdown.R
import com.example.countdown.presentation.screens.onboarding_screen.TimeBox
import com.example.countdown.presentation.screens.settings_screen.viewmodels.SettingsAccountViewModel
import java.text.SimpleDateFormat
import kotlin.math.min

@Composable
fun TemplateScreen(
    navHostController: NavHostController,
    icon: Int = R.drawable.ic_baseline_account_circle_24,
    item: @Composable ColumnScope.(Boolean) -> Unit
) {

    var edit by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        IconButton(onClick = {
            navHostController.popBackStack()
        }) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopStart),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back button"
            )
        }

        IconButton(
            onClick = { edit = !edit },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Icon(
                imageVector = if (edit) Icons.Default.Done else Icons.Default.Edit,
                contentDescription = "Edit Button",
                tint = MaterialTheme.colors.primary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = MaterialTheme.colors.primaryVariant),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Icon",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            item(edit)

        }

    }
}

@Composable
fun AccountItem(
    text: String,
    readOnly: Boolean,
    viewModel: SettingsAccountViewModel,
    onChange: (String) -> Unit
) {

    val user = viewModel.userState

    var name by remember {
        mutableStateOf(user.value.name)
    }

    LaunchedEffect(key1 = user.value) {
        name = user.value.name
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            fontWeight = Bold
        )

        TextField(
            value = name,
            onValueChange = { value ->
                name = value
                onChange(name)
            },
            singleLine = true,
            readOnly = !readOnly,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .height(55.dp)
                .width(240.dp)
        )

    }
}

@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    viewModel: SettingsAccountViewModel = hiltViewModel()
) {

    val userModel = viewModel.userState

    var id = 0

    if (userModel.value.name != "Default User") {

        var user = userModel.value

        TemplateScreen(
            navHostController = navHostController,
            icon = R.drawable.ic_baseline_settings_24
        ) {

            SettingsItem(
                text = "Wake Up time: ",
                hour = userModel.value.wake_up.toString().split(" ")[3].split(":")[0].toInt(),
                minute = userModel.value.wake_up.toString().split(" ")[3].split(":")[1].toInt(),
                edit = it,
                label = "wakeup"
            ) { hw, mw, hs , ms ->

                if (it) {
                    id = 1
                } else if (id == 1){

                    val wakeUp = SimpleDateFormat("HH:mm").parse("$hw:$mw")
                    val goDown = SimpleDateFormat("HH:mm").parse("$hs:$ms")

                    user = user.copy(
                        wake_up = wakeUp,
                        go_down = goDown
                    )
                    viewModel.updateUser(userModel = user)
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            SettingsItem(
                text = "Sleeping Time: ",
                hour = userModel.value.go_down.toString().split(" ")[3].split(":")[0].toInt(),
                minute = userModel.value.go_down.toString().split(" ")[3].split(":")[1].toInt(),
                edit = it,
                label = "sleep"
            ) {  hw, mw, hs , ms ->

                if (it) {
                    id = 1
                } else if (id == 1) {
                    val wakeUp = SimpleDateFormat("HH:mm").parse("$hw:$mw")
                    val goDown = SimpleDateFormat("HH:mm").parse("$hs:$ms")

                    user = user.copy(
                        wake_up = wakeUp,
                        go_down = goDown
                    )

                    viewModel.updateUser(userModel = user)

                }

            }

        }
    }
}

@Composable
fun SettingsItem(
    text: String,
    hour: Int,
    minute: Int,
    edit: Boolean,
    label: String,
    onChange: (String, String, String, String) -> Unit
) {

    var hWakeup = if (label == "wakeup") hour.toString() else ""

    var mWakeup = if (label == "wakeup") minute.toString() else ""

    var hSleep = if (label == "sleep") hour.toString() else ""

    var mSleep = if (label == "sleep") minute.toString() else ""

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            fontWeight = SemiBold,
            fontSize = 20.sp
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TimeBox(
                background = MaterialTheme.colors.secondary,
                tint = MaterialTheme.colors.primaryVariant,
                textColor = MaterialTheme.colors.primaryVariant,
                label = "Hour $label",
                height = 120.dp,
                width = 80.dp,
                textSize = 20.sp,
                iconSize = 24.dp,
                hour = hour,
                minute = minute,
                edit = edit
            ) { a, b ->

                when(b) {
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

                onChange(hWakeup, mWakeup, hSleep, mSleep)
            }

            TimeBox(
                background = MaterialTheme.colors.primaryVariant,
                tint = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.secondary,
                label = "Minutes $label",
                height = 120.dp,
                width = 80.dp,
                textSize = 20.sp,
                iconSize = 24.dp,
                hour = hour,
                minute = minute,
                edit = edit
            ) { a, b ->

                when(b) {
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
                onChange(hWakeup, mWakeup, hSleep, mSleep)
            }

        }

    }
}

@Composable
fun AccountScreen(
    navHostController: NavHostController,
    viewModel: SettingsAccountViewModel = hiltViewModel()
) {

    val user = viewModel.userState

    var userModel = user.value

    var id = 0

    TemplateScreen(navHostController = navHostController) {
        if (it) {
            id = 1
        } else if (id == 1) {
            viewModel.updateUser(userModel = userModel)
        }

        AccountItem(text = "Name: ", readOnly = it, viewModel = viewModel) { name ->
            userModel = userModel.copy(
                name = name
            )
        }
    }
    
}