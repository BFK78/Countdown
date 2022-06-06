package com.example.countdown.data.remote

import com.example.countdown.data.remote.dto.QuotesDto
import retrofit2.http.GET

interface QuotesApi {

    @GET("/random")
    suspend fun getQuote(): QuotesDto

}