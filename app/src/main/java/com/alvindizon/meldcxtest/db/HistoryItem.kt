package com.alvindizon.meldcxtest.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "historyDb", indices = arrayOf(Index(value = ["dateTime"],  unique = true)))
class HistoryItem (
    var url: String,
    var dateTime: Long,
    var fileName: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
}