package com.danielecampogiani.emilyevents.location

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import utils.SimpleLocationListener


class LocationViewModel(application: Application) : AndroidViewModel(application) {

    val locationLiveData: MutableLiveData<LocationState> = MutableLiveData()
    private val locationManager: LocationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val locationListener: LocationListener = object : SimpleLocationListener() {
        override fun onLocationChanged(location: Location) {
            locationLiveData.value = LocationState.Result(location.latitude, location.longitude)
            locationManager.removeUpdates(this)
        }
    }

    init {
        locationLiveData.value = LocationState.Loading
    }


    fun getLocation(): LiveData<LocationState> {
        locationManager.removeUpdates(locationListener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        return locationLiveData
    }

    override fun onCleared() {
        locationManager.removeUpdates(locationListener)
    }
}