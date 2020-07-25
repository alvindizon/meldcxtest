package com.alvindizon.meldcxtest.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alvindizon.meldcxtest.core.Const
import com.alvindizon.meldcxtest.core.ui.BaseViewModel
import com.alvindizon.meldcxtest.db.HistoryDao
import com.alvindizon.meldcxtest.db.HistoryItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel(private val historyDao: HistoryDao) : BaseViewModel() {

    private lateinit var uiList: MutableList<HistoryUIItem>

    private val _uiState = MutableLiveData<HistoryUIState>()
    val uiState: LiveData<HistoryUIState>? get() = _uiState

    // this function will try to search for an URL in the DB. if successful,
    // the contents of the list will be replaced and be populated with matching records
    fun searchByUrl(url: String) {
        compositeDisposable.add(historyDao.getRecordByUrl(url)
            .flatMap{ transformDbToUiItems(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _uiState.value = LOADING }
            .subscribeBy(
                onSuccess = {
                    _uiState.value = SUCCESS(it)
                    uiList = it
                },
                onError = { error ->
                    error.printStackTrace()
                    _uiState.value = error.message?.let {
                        ERROR(it)
                    }
                }
            )
        )
    }

    // on initial load, the history list shows all the items in the DB.
    fun fetchData() {
        compositeDisposable.add(historyDao.getAllItems()
            .flatMap{ transformDbToUiItems(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _uiState.value = SUCCESS(it)
                    uiList = it
                },
                onError = { error ->
                    error.printStackTrace()
                    _uiState.value = error.message?.let {
                        ERROR(it)
                    }
                }
            )
        )
    }

    // this function is called from the ViewModel, this was done so that I have access to the BinDao
    fun deleteItem(historyUIItem: HistoryUIItem) {
        compositeDisposable.add(historyDao.deleteRecordByFileName(historyUIItem.fileName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    uiList.remove(historyUIItem)
                    _uiState.value = SUCCESS(uiList)
                },
                onError = { error ->
                    error.printStackTrace()
                    _uiState.value = error.message?.let {
                        ERROR(it)
                    }
                }
            )
        )
    }

    // this function transforms the records found in the DB to the corresponding items to be displayed
    // in the UI. I had to use toMutableList so that I can use remove() on the uiList
    fun transformDbToUiItems(dbList: List<HistoryItem>): Single<MutableList<HistoryUIItem>> {
        return Single.just(
            dbList.map{ HistoryUIItem(it.url, formatMillis(it.dateTime), it.fileName) }.toMutableList()
        )
    }

    fun formatMillis(millis: Long): String {
        val date = Date(millis)
        // X for +08, Z or XX for +0800, XXX for +08:00
        val df = SimpleDateFormat(Const.DATE_TIME_FORMAT, Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("GMT+8")
        return df.format(date)
    }
}