package com.example.countdown.presentation.screens.account_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.countdown.R
import com.example.countdown.presentation.screens.home_screen.TimeBox


@Composable
fun AccountScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopStart),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back button"
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                 Icon(
                     painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                     contentDescription = "Icon",
                     tint = MaterialTheme.colors.secondary
                 )
            }
            
            TimeChangingBox(text = "Wake up time:")
            
            TimeChangingBox(text = "Sleeping Time:")

        }

    }
}

@Composable
fun TimeChangingBox(
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = text
        )

        Row(
            modifier = Modifier
        ) {
            com.example.countdown.presentation.screens.onboarding_screen.TimeBox(
                background = MaterialTheme.colors.secondary,
                tint = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.surface,
                label = ""
            ) { i, o ->

            }
            com.example.countdown.presentation.screens.onboarding_screen.TimeBox(
                background = MaterialTheme.colors.primaryVariant,
                tint = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.primary,
                label = ""
            ) { o, p ->

            }
        }

    }
}