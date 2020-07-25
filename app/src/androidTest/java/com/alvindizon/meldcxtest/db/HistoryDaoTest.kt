package com.alvindizon.meldcxtest.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.reactivex.rxkotlin.subscribeBy
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.junit.*

@RunWith(MockitoJUnitRunner::class)
class HistoryDaoTest {

    // region constants
    companion object {
        val SAMPLE = HistoryItem(
            "https://google.com",
            System.currentTimeMillis(),
            "${System.currentTimeMillis()} + _IMG"
        )
    }
    // endregion constants

    // region helper fields
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var historyDb: HistoryDb
    // endregion helper fields

    private lateinit var SUT: HistoryDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        historyDb = Room.inMemoryDatabaseBuilder(context, HistoryDb::class.java)
            .allowMainThreadQueries()
            .build()

        SUT = historyDb.historyDao()
    }

    @After
    fun tearDown() {
        historyDb.close()
    }

    @Test
    fun historyDao_success_insert() {
        // Arrange
        // Act
        SUT.insert(SAMPLE).blockingAwait()

        // Assert
        SUT.getAllItems().test().assertValue{ list ->
            list.isNotEmpty()
        }
    }

    @Test
    fun historyDao_success_delete() {
        // Arrange
        SUT.insert(SAMPLE).blockingAwait()

        // Act
        SUT.getAllItems().subscribeBy(
            onSuccess = {
                SUT.deleteRecordByFileName(SAMPLE.fileName).blockingAwait()
            },
            onError = { it.printStackTrace() }
        )

        // Assert
        SUT.getAllItems().test().assertValue {
            it.isEmpty()
        }
    }

    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes

}