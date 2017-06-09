package com.danielecampogiani.emilyevents.location


sealed class LocationState {

    object Loading : LocationState()
    data class Result(val latitude: Float, val longitude: Float) : LocationState()
    object Error : LocationState()
}