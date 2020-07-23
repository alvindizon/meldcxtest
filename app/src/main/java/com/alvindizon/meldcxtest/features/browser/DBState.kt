package com.alvindizon.meldcxtest.features.browser

sealed class DBState

object SAVING_TO_DB : DBState()

data class ERROR(
    var error : String
): DBState()

object SAVE_SUCCESS : DBState()

