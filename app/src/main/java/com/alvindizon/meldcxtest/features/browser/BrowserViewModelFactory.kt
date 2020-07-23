package com.alvindizon.meldcxtest.features.browser

import android.content.ContextWrapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvindizon.meldcxtest.db.HistoryDao
import javax.inject.Inject
import javax.inject.Singleton

// I could have used a more generic version of the ViewModelFactory that can take care
// of ViewModels with different constructors, but for this case it seems to be an overkill
@Singleton
class BrowserViewModelFactory @Inject constructor(private val contextWrapper: ContextWrapper,
private val historyDao: HistoryDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowserViewModel::class.java)) {
            return BrowserViewModel(contextWrapper, historyDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}