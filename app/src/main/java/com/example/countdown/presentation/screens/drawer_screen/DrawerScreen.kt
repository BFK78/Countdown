package com.example.countdown.presentation.screens.drawer_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.countdown.presentation.screens.home_screen.QuoteBox
import com.example.countdown.presentation.util.Screens

val drawerScreen = listOf(
    Screens.Account,
    Screens.Settings
)

@Composable
fun DrawerScreen(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primaryVariant)
    ) {

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.primaryVariant,
            thickness = 1.dp
        )
        
        QuoteBox(
            background = Color.White
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        drawerScreen.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when(it.title) {
                            Screens.Account.title -> {
                                navHostController.navigate(Screens.Account.route)
                            }
                            Screens.Settings.title -> {
                                navHostController.navigate(Screens.Settings.route)
                            }
                        }
                    }
                    .padding(start = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = CenterVertically
            ) {


                IconButton(onClick = {
                    when(it.title) {
                        Screens.Account.title -> {
                            navHostController.navigate(Screens.Account.route)
                        }
                        Screens.Settings.title -> {
                            navHostController.navigate(Screens.Settings.route)
                        }
                    }
                }) {
                    Icon(
                        painter = painterResource(id = it.icon!!),
                        contentDescription = "Drawer Icon",
                        tint = MaterialTheme.colors.secondary
                    )
                }

                Text(
                    text = it.title
                )
            }
        }

    }
}