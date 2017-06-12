package com.danielecampogiani.emilyevents.dagger

import com.danielecampogiani.emilyevents.location.LocationViewModel
import com.danielecampogiani.network.facebook.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(locationViewModel: LocationViewModel)
}