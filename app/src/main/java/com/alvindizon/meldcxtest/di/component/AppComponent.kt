package com.alvindizon.meldcxtest.di.component

import com.alvindizon.meldcxtest.MeldCxTestApp
import com.alvindizon.meldcxtest.di.module.AppModule
import com.alvindizon.meldcxtest.di.module.DatabaseModule
import com.alvindizon.meldcxtest.features.browser.BrowserActivity
import com.alvindizon.meldcxtest.features.history.HistoryActivity
import dagger.Component
import javax.inject.Singleton

// I opted to just have one component for this app. I usually have components for Activities and Services,
// but this is enough for this case
@Component(modules = [AppModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {
    fun inject(application: MeldCxTestApp)
    fun inject(activity: HistoryActivity)
    fun inject(activity: BrowserActivity)
}