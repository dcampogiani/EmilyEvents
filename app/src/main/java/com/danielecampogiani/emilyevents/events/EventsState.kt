package com.danielecampogiani.emilyevents.events

import com.danielecampogiani.network.facebook.FacebookEvent

sealed class EventsState {

    object Loading : EventsState()
    data class Result(val events: List<FacebookEvent>) : EventsState()
    data class Error(val message: String) : EventsState()
}