package com.danielecampogiani.network.facebook

import com.danielecampogiani.network.RetrofitFacebook
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject


class FacebookAPI @Inject constructor(retrofit: Retrofit) {

    private val retrofitFacebook: RetrofitFacebook = retrofit.create(RetrofitFacebook::class.java)

    fun getEvents(latitude: String, longitude: String, distance: String?, sort: String?): Call<FacebookResult> {
        return retrofitFacebook.getEvents(latitude, longitude, distance, sort)
    }
}