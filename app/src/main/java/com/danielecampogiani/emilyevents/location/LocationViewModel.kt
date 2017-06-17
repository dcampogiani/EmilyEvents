package com.danielecampogiani.emilyevents.location

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices


class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData: MutableLiveData<LocationState> = MutableLiveData()
    private val locationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    init {
        locationLiveData.value = LocationState.Loading
    }


    fun getLocation(): LiveData<LocationState> {
        locationProviderClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                locationLiveData.value = LocationState.Result(result.latitude, result.longitude)
            } else {
                task.exception?.message?.let { locationLiveData.value = LocationState.Error(it) }
            }
        }
        return locationLiveData
    }
}