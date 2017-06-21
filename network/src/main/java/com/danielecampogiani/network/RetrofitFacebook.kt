package com.danielecampogiani.network

import com.danielecampogiani.network.facebook.FacebookResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


internal interface RetrofitFacebook {

    @GET("events")
    fun getEvents(@Query("lat") latitude: String, @Query("lng") longitude: String, @Query("distance") distance: String?, @Query("sort") sort: String?, @Query("since") since: String?, @Query("until") until: String?): Call<FacebookResult>
}