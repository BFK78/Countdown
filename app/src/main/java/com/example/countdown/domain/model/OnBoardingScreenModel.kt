package com.example.countdown.domain.model

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

data class OnBoardingScreenModel @OptIn(ExperimentalPagerApi::class) constructor(
    val question: Int,
    val action: @Composable ColumnScope.( pagerState: PagerState, navHostController: NavHostController,onSave: () -> Unit, onChange: (String, String) -> Unit ) -> Unit
)
