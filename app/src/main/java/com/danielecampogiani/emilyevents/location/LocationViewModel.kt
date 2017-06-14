package com.danielecampogiani.emilyevents.location

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.lang.Exception


class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData: MutableLiveData<LocationState> = MutableLiveData()
    private val locationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    init {
        locationLiveData.value = LocationState.Loading
    }

    private val locationListener = OnSuccessListener<Location> { location -> location?.let { locationLiveData.value = LocationState.Result(it.latitude, it.longitude) } }


    fun getLocation(): LiveData<LocationState> {
        locationProviderClient.lastLocation.addOnSuccessListener { locationListener }
                .addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(p0: Exception) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
        return locationLiveData
    }

    override fun onCleared() {
        //locationProviderClient clear
    }
}