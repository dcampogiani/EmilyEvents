package com.danielecampogiani.network.facebook

import java.util.*


data class FacebookEvent(
        val id: String,
        val name: String,
        val type: String,
        val coverPicture: String,
        val profilePicture: String,
        val description: String,
        val distance: String,
        val startTime: Date,
        val endTime: Date,
        val timeFromNow: Int,
        val stats: Stats,
        val venue: Venue)

data class Stats(val attending: Int,
                 val declined: Int,
                 val maybe: Int,
                 val noreply: Int)

data class Venue(val id: String,
                 val name: String,
                 val about: String,
                 val coverPicture: String,
                 val profilePicture: String,
                 val category: String,
                 val location: Location)

data class Location(val city: String,
                    val country: String,
                    val latitude: Float,
                    val longitude: Float,
                    val street: String,
                    val zip: String)