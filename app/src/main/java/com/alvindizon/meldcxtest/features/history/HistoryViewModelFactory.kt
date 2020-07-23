package com.alvindizon.meldcxtest.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvindizon.meldcxtest.db.HistoryDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryViewModelFactory @Inject constructor(private val historyDao: HistoryDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(historyDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}