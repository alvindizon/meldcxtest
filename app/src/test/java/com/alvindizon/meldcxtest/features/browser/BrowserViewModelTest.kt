package com.alvindizon.meldcxtest.features.browser

import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alvindizon.meldcxtest.db.HistoryDao
import com.alvindizon.meldcxtest.db.HistoryItem
import com.alvindizon.meldcxtest.util.RxSchedulerRule
import com.alvindizon.meldcxtest.util.testObserver
import com.google.common.truth.Truth
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class BrowserViewModelTest {

    // region constants
    companion object {
        const val URL = "https://google.com"
        val SAMPLE = HistoryItem(
            "https://google.com",
            System.currentTimeMillis(),
            "${System.currentTimeMillis()} + _IMG"
        )
    }

    // endregion constants

    // region helper fields
    @Mock
    private lateinit var historyDao: HistoryDao

    @Mock
    private lateinit var contextWrapper: ContextWrapper

    @Mock
    private lateinit var bitmap: Bitmap

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    // endregion helper fields

    lateinit var SUT: BrowserViewModel

    @Before
    fun setup() {
        SUT = BrowserViewModel(contextWrapper, historyDao)
    }

    @Test
    fun browserVm_success_isSubscribed() {
        SUT.saveBitmapToInternalStorage(URL, bitmap).test().assertSubscribed()
    }

    @Test
    fun browserVm_success_correctStatesOnSave() {
        val dbStatus = SUT.dbState?.testObserver()
        val spy = Mockito.spy(SUT)
        insert()
        `when`(spy.saveBitmapToInternalStorage(URL, bitmap)).thenReturn(Single.just(SAMPLE))
        SUT.saveBitmapAndCreateRecord(URL, bitmap)

        Truth.assert_()
            .that(dbStatus?.observedValues)
            .isEqualTo(listOf(SAVING_TO_DB, SAVE_SUCCESS))
    }

    // region helper methods
    fun insert() {
        `when`(historyDao.insert(SAMPLE)).thenReturn(Completable.complete())
    }
    // endregion helper methods

    // region helper classes

    // endregion helper classes

}