package com.danielecampogiani.emilyevents.events

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

class EventsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject lateinit var facebookAPI: FacebookAPI


    private val eventsLiveData: MutableLiveData<EventsState> = MutableLiveData()


    init {
        EmilyEventsApp.appComponent.inject(this)
        eventsLiveData.value = EventsState.Loading
    }

    fun getUILiveData(latitude: String, longitude: String): LiveData<EventsState> {

        facebookAPI.getEvents(latitude, longitude, "1000", "popularity")
                .enqueue(object : Callback<FacebookResult> {

                    override fun onResponse(call: Call<FacebookResult>, response: Response<FacebookResult>) {
                        if (response.isSuccessful) {
                            eventsLiveData.value = response.body()?.events?.let { EventsState.Result(it) }
                        } else {
                            eventsLiveData.value = response.errorBody()?.string()?.let { EventsState.Error(it) }
                        }
                    }

                    override fun onFailure(call: Call<FacebookResult>, t: Throwable) {
                        eventsLiveData.value = t.message?.let { EventsState.Error(it) }
                    }

                })

        return eventsLiveData
    }

}

