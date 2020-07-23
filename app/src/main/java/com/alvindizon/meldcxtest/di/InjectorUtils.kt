package com.alvindizon.meldcxtest.di

import com.alvindizon.meldcxtest.MeldCxTestApp
import com.alvindizon.meldcxtest.di.component.AppComponent

object InjectorUtils {

    @JvmStatic
    fun getAppComponent(): AppComponent = MeldCxTestApp.getAppComponent()

}