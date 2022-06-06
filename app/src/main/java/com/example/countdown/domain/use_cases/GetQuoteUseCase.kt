package com.example.countdown.domain.use_cases

import com.example.countdown.domain.repository.QuoteRepository

class GetQuoteUseCase(
    private val quoteRepository: QuoteRepository
) {

    operator fun invoke() = quoteRepository.getQuote()

}