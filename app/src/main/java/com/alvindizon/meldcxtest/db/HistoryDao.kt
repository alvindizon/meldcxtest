package com.alvindizon.meldcxtest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class HistoryDao {

    @Insert
    abstract fun insert(historyItem: HistoryItem): Completable

    @Insert
    abstract fun insert(historyItem: List<HistoryItem>): Completable

    @Query("DELETE FROM historyDb")
    abstract fun clearDb(): Completable

    @Query("SELECT * FROM historyDb WHERE url LIKE :url")
    abstract fun getRecordByUrl(url: String): Single<List<HistoryItem>>

    @Query("DELETE FROM historyDb WHERE fileName LIKE :uri")
    abstract fun deleteRecordByUri(uri: String): Completable

    @Query("SELECT * FROM historyDb")
    abstract fun getAllItems(): Single<List<HistoryItem>>


}