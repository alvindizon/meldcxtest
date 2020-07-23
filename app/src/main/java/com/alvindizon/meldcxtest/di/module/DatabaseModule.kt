package com.alvindizon.meldcxtest.di.module

import android.content.Context
import androidx.room.Room
import com.alvindizon.meldcxtest.db.HistoryDao
import com.alvindizon.meldcxtest.db.HistoryDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideHistoryDb(appContext: Context) : HistoryDb =
        Room.databaseBuilder(appContext, HistoryDb::class.java, "meldcxtest-db")
            .build()

    @Provides
    @Singleton
    fun provideHistoryDao(historyDb: HistoryDb) : HistoryDao = historyDb.historyDao()
}