package com.alvindizon.meldcxtest.features.browser

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alvindizon.meldcxtest.core.ui.BaseViewModel
import com.alvindizon.meldcxtest.db.HistoryDao
import com.alvindizon.meldcxtest.db.HistoryItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class BrowserViewModel(
    private val contextWrapper: ContextWrapper,
    private val historyDao: HistoryDao
) : BaseViewModel() {

    private val _dbState = MutableLiveData<DBState>()
    val dbState: LiveData<DBState>? get() = _dbState

    // this function chains the saving of the screenshot to local storage and the creation
    // of the corresponding DB record.
    fun saveBitmapAndCreateRecord(url: String, bitmap: Bitmap) {
        compositeDisposable.add(saveBitmapToInternalStorage(url, bitmap)
            .flatMapCompletable{
                historyDao.insert(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _dbState.value = SAVING_TO_DB }
            .subscribeBy(
                onComplete = { _dbState.value = SAVE_SUCCESS },
                onError = { error ->
                    error.printStackTrace()
                    _dbState.value = error.message?.let {
                        ERROR(it)
                    }
                }
            )
        )
    }

    // This function uses the ContextWrapper to access the internal app storage and saves the screenshot
    // to that location.
    // On success, this function emits a new HistoryItem that will be saved to the DB
    fun saveBitmapToInternalStorage(url: String, bitmap: Bitmap) : Single<HistoryItem> {
        return Single.create {
            var file = contextWrapper.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE)
            val millis = System.currentTimeMillis()
            file = File(file, millis.toString() + "_IMG.jpg")

            try {
                val fos: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                it.tryOnError(e)
            }

            it.onSuccess(HistoryItem(url, millis, file.name))
        }
    }

}