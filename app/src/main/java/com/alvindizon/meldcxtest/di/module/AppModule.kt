package com.alvindizon.meldcxtest.di.module

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application){

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = this.application.applicationContext

    @Provides
    @Singleton
    fun provideContextWrapper(context: Context): ContextWrapper = ContextWrapper(context)
}