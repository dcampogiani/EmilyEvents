package com.danielecampogiani.emilyevents.dagger

import com.danielecampogiani.emilyevents.events.EventsViewModel
import com.danielecampogiani.network.facebook.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(eventsViewModel: EventsViewModel)
}