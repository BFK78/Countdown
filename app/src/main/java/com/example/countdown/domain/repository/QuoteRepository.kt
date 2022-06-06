package com.example.countdown.domain.repository

import com.example.countdown.common.util.Resources
import com.example.countdown.domain.model.QuotesModel
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun getQuote(): Flow<Resources<QuotesModel>>

}