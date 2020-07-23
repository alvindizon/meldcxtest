package com.alvindizon.meldcxtest

import android.app.Application
import com.alvindizon.meldcxtest.di.component.AppComponent
import com.alvindizon.meldcxtest.di.component.DaggerAppComponent
import com.alvindizon.meldcxtest.di.module.AppModule

class MeldCxTestApp : Application() {

    companion object {
        private var INSTANCE: MeldCxTestApp? = null

        @JvmStatic
        fun getAppComponent() = get().appComponent

        @JvmStatic
        fun get(): MeldCxTestApp = INSTANCE!!
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}