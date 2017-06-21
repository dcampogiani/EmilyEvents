package com.danielecampogiani.network.facebook

import com.danielecampogiani.network.RetrofitFacebook
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject


class FacebookAPI @Inject constructor(retrofit: Retrofit) {

    private val retrofitFacebook: RetrofitFacebook = retrofit.create(RetrofitFacebook::class.java)

    fun getEvents(request: FacebookRequest): Call<FacebookResult> {
        return retrofitFacebook.getEvents(request.latitude, request.longitude, request.distance, request.sort.parameter, request.time?.since, request.time?.until)
    }

}