package com.alvindizon.meldcxtest.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryItem::class), version = 1)
abstract class HistoryDb : RoomDatabase() {
    abstract fun historyDao() : HistoryDao
}