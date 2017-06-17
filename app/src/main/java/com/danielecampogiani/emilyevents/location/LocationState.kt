package com.danielecampogiani.emilyevents.location


sealed class LocationState {

    object Loading : LocationState()
    data class Result(val latitude: Double, val longitude: Double) : LocationState()
    data class Error(val message: String) : LocationState()
}