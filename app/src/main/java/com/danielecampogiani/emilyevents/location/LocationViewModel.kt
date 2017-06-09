package com.danielecampogiani.emilyevents.location

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.danielecampogiani.emilyevents.EmilyEventsApp
import com.danielecampogiani.network.facebook.FacebookAPI
import com.danielecampogiani.network.facebook.FacebookResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class LocationViewModel(application: Application) : AndroidViewModel(application) {

    @Inject lateinit var facebookAPI: FacebookAPI


    val locationLiveData: MutableLiveData<LocationState> = MutableLiveData()

    init {
        EmilyEventsApp.appComponent.inject(this)
        locationLiveData.value = LocationState.Loading
        facebookAPI.getEvents("44.4992192", "11.2616451", "1000", "popularity")
                .enqueue(object : Callback<FacebookResult> {
                    override fun onResponse(call: Call<FacebookResult>?, response: Response<FacebookResult>?) {

                        response?.let {
                            if (response.isSuccessful) {
                                locationLiveData.value = LocationState.Result(44.4992192f, 11.2616451f)
                            } else {
                                locationLiveData.value = LocationState.Error
                            }
                        }
                    }

                    override fun onFailure(call: Call<FacebookResult>?, t: Throwable?) {
                        locationLiveData.value = LocationState.Error
                    }
                })
    }


    fun getLocation(): LiveData<LocationState> {
        return locationLiveData
    }

}