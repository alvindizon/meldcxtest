package com.alvindizon.meldcxtest.features.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alvindizon.meldcxtest.db.HistoryDao
import com.alvindizon.meldcxtest.db.HistoryItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryViewModelTest {

    // region constants
    companion object {
        val DBLIST = mutableListOf(HistoryItem(
            "https://google.com",
            System.currentTimeMillis(),
            "${System.currentTimeMillis()} + _IMG"
        ))
    }
    // endregion constants

    // region helper fields
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var historyDao: HistoryDao
    // endregion helper fields

    lateinit var SUT: HistoryViewModel

    @Before
    fun setup() {
        SUT = HistoryViewModel(historyDao)
    }

    @Test
    @Throws(Exception::class)
    fun historyVm_success_uiItemsNotEmpty()  {
        // Arrange
        // Act
        // Assert
        SUT.transformDbToUiItems(DBLIST).test().assertValue{
            it.isNotEmpty()
        }
    }


    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes

}