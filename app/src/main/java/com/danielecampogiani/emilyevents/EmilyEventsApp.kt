package com.danielecampogiani.emilyevents

import android.app.Application
import com.danielecampogiani.emilyevents.dagger.AppComponent
import com.danielecampogiani.emilyevents.dagger.AppModule
import com.danielecampogiani.emilyevents.dagger.DaggerAppComponent
import net.danlew.android.joda.JodaTimeAndroid


class EmilyEventsApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        JodaTimeAndroid.init(this)
    }
}