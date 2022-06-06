package com.example.countdown.data.remote.repository

import com.example.countdown.common.util.Resources
import com.example.countdown.data.local.dao.QuotesDao
import com.example.countdown.data.remote.QuotesApi
import com.example.countdown.domain.model.QuotesModel
import com.example.countdown.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class QuoteRepositoryImplementation(
    private val dao: QuotesDao,
    private val api: QuotesApi
): QuoteRepository {
    override fun getQuote(): Flow<Resources<QuotesModel>> = flow {

//        emit(Resources.Loading())

        val localQuote = dao.getQuote()

        emit(Resources.Loading(data = localQuote))

        try {

            val remoteQuote = api.getQuote()
            dao.insertQuotes(remoteQuote.toQuotesModel())

        } catch (e: HttpException) {
            emit(Resources.Error(data = localQuote, message = e.message()))

        } catch (e: IOException) {
            emit(Resources.Error(data = localQuote, message = e.message))
        }

        val newQuote = dao.getQuote()

        emit(Resources.Success(data = newQuote))

    }
}