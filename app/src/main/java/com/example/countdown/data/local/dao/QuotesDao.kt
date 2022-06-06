package com.example.countdown.data.local.dao

import androidx.room.*
import com.example.countdown.domain.model.QuotesModel

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotesModel: QuotesModel)

    @Delete
    suspend fun deleteQuotes(quotesModel: QuotesModel)

    @Query("SELECT * FROM QuotesModel WHERE id = 1")
    suspend fun getQuote(): QuotesModel

}