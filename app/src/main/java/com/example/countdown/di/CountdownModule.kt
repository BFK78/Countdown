package com.example.countdown.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.countdown.common.constants.Constants.BASE_URL
import com.example.countdown.data.local.dao.CountdownDayDao
import com.example.countdown.data.local.dao.CountdownUserDao
import com.example.countdown.data.local.dao.CountdownWeekDao
import com.example.countdown.data.local.dao.QuotesDao
import com.example.countdown.data.local.database.CountdownDatabase
import com.example.countdown.data.local.repository.DayTimerRepositoryImplementation
import com.example.countdown.data.local.repository.UserRepositoryImplementation
import com.example.countdown.data.local.repository.WeekTimerRepositoryImplementation
import com.example.countdown.data.remote.QuotesApi
import com.example.countdown.data.remote.repository.QuoteRepositoryImplementation
import com.example.countdown.domain.repository.DayTimerRepository
import com.example.countdown.domain.repository.QuoteRepository
import com.example.countdown.domain.repository.UserRepository
import com.example.countdown.domain.repository.WeekTimerRepository
import com.example.countdown.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CountdownModule {

    //Database
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CountdownDatabase {
       return Room.databaseBuilder(
           context,
           CountdownDatabase::class.java,
           "Countdown Database"
       ).build()
    }

    //Dao
    @Provides
    @Singleton
    fun provideQuoteDao(
        database: CountdownDatabase
    ): QuotesDao {
        return database.quotesDao
    }

    @Provides
    @Singleton
    fun provideUserDao(
        database: CountdownDatabase
    ): CountdownUserDao {
        return database.userDao
    }

    @Provides
    @Singleton
    fun provideDayDao(
        database: CountdownDatabase
    ): CountdownDayDao { return database.dayDao }

    @Provides
    @Singleton
    fun provideWeekDao(
        database: CountdownDatabase
    ): CountdownWeekDao { return database.weekDao }


    //Repository
    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: CountdownUserDao
    ): UserRepository {
        return UserRepositoryImplementation(userDao = userDao)
    }

    @Provides
    @Singleton
    fun provideDayTimerRepository(
        dayDao: CountdownDayDao
    ): DayTimerRepository {
        return DayTimerRepositoryImplementation(dayDao = dayDao)
    }

    @Provides
    @Singleton
    fun provideWeekTimerRepository(
        weekDao: CountdownWeekDao
    ): WeekTimerRepository {
        return WeekTimerRepositoryImplementation(weekDao = weekDao)
    }

    @Provides
    @Singleton
    fun provideQuotesRepository(
        quotesDao: QuotesDao,
        quotesApi: QuotesApi
    ): QuoteRepository {
        return QuoteRepositoryImplementation(
            dao = quotesDao,
            api = quotesApi
        )
    }

    //UseCases
    @Provides
    @Singleton
    fun provideUserUseCase(
        repository: UserRepository
    ): UserUseCase = UserUseCase(userRepository = repository)

    @Provides
    @Singleton
    fun provideInsertDayTimerUseCase(
        repository: DayTimerRepository
    ) = InsertDayTimerUseCase(dayTimerRepository = repository)

    @Provides
    @Singleton
    fun provideInsertWeekTimerUseCae(
        repository: WeekTimerRepository
    ) = InsertWeekTimerUseCase(weekTimerRepository = repository)

    @Provides
    @Singleton
    fun provideGetQuoteUseCase(
        repository: QuoteRepository
    ): GetQuoteUseCase {
        return GetQuoteUseCase(quoteRepository = repository)
    }

    @Provides
    @Singleton
    fun provideGetTimeIntervalUseCase(
        repository: DayTimerRepository
    ):GetTimeIntervalUseCase {
        return GetTimeIntervalUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllDayTimerUseCase(
        repository: DayTimerRepository
    ): DeleteAllDayTimerUseCase {
        return DeleteAllDayTimerUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetAllWeekDataUseCase(
        repository: WeekTimerRepository
    ): GetAllWeekDataUseCase {
        return GetAllWeekDataUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideInsertWeekTimerListUseCase(
        repository: WeekTimerRepository
    ): InsertWeekTimerListUseCase {
        return InsertWeekTimerListUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllWeekTimerUseCase(
        repository: WeekTimerRepository
    ): DeleteAllWeekTimerListUseCase {
        return DeleteAllWeekTimerListUseCase(repository = repository)
    }

    //api
    @Provides
    @Singleton
    fun provideQuoteApi(): QuotesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApi::class.java)
    }

}