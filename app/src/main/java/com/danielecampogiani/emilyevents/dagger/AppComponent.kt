package com.danielecampogiani.emilyevents.dagger

import com.danielecampogiani.emilyevents.MainActivity
import com.danielecampogiani.network.facebook.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface  AppComponent {

    fun inject( mainActivity: MainActivity)
}