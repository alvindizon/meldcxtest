package com.alvindizon.meldcxtest.features.history

sealed class HistoryUIState

object LOADING : HistoryUIState()

data class ERROR(
    var error : String
): HistoryUIState()

data class SUCCESS(
    var historyUiItemList: List<HistoryUIItem>
): HistoryUIState()

