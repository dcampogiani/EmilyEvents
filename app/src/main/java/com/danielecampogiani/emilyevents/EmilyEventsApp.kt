package com.danielecampogiani.emilyevents

import android.app.Application
import com.danielecampogiani.emilyevents.dagger.AppComponent
import com.danielecampogiani.emilyevents.dagger.DaggerAppComponent


class EmilyEventsApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}