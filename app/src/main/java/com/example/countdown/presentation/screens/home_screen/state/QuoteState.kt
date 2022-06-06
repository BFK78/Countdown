package com.example.countdown.presentation.screens.home_screen.state

import com.example.countdown.domain.model.QuotesModel

data class QuoteState(
    val quote: QuotesModel? = null,
    val isLoading: Boolean = true
)
