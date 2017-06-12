package com.danielecampogiani.emilyevents.dagger

import android.content.Context
import com.danielecampogiani.emilyevents.EmilyEventsApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: EmilyEventsApp) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return app
    }
}