package com.danielecampogiani.emilyevents.location

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData: MutableLiveData<LocationState> = MutableLiveData()
    private val locationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    init {
        locationLiveData.value = LocationState.Loading
    }

    private val locationListener = OnSuccessListener<Location> { location -> location?.let { locationLiveData.value = LocationState.Result(it.latitude, it.longitude) } }


    fun getLocation(): LiveData<LocationState> {
        locationProviderClient.lastLocation.addOnCompleteListener(object : OnCompleteListener<Location> {
            override fun onComplete(task: Task<Location>) {
                //TODO check if task is successful
                val result = task.result
                locationLiveData.value = LocationState.Result(result.latitude, result.longitude)
            }
        })
        return locationLiveData
    }

    override fun onCleared() {
        //locationProviderClient clear?
    }
}