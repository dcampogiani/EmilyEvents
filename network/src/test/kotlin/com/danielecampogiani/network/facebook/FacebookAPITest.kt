package com.danielecampogiani.network.facebook

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FacebookAPITest {

    lateinit var sut : FacebookAPI

    @Before
    fun setUp() {

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://blooming-shelf-93950.herokuapp.com")
                .build()

        sut = FacebookAPI(retrofit)
    }

    @Test
    fun getEvents() {
        val call = sut.getEvents("44.4992192", "11.2616451", "1000", "popularity")
        val response = call.execute()
        val body = response.body()
        assertNotNull(body)
    }

}