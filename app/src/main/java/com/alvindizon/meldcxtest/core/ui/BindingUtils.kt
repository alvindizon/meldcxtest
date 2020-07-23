package com.alvindizon.meldcxtest.core.ui

import android.view.View
import androidx.databinding.BindingAdapter
import com.alvindizon.meldcxtest.features.history.HistoryUIState
import com.alvindizon.meldcxtest.features.history.LOADING

// these functions serve as a kind of custom attribute that can be applied on the XML
object BindingUtils  {

    @JvmStatic
    @BindingAdapter("historyProgressVisibility")
    fun setHistoryProgressVisibility(view: View, uiState: HistoryUIState?) {
        view.visibility = when(uiState) {
            LOADING -> View.VISIBLE
            else -> View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("rvFrameVisibility")
    fun setRvFrameVisibility(view: View, uiState: HistoryUIState?) {
        view.visibility = when(uiState) {
            LOADING -> View.GONE
            else -> View.VISIBLE
        }
    }
}