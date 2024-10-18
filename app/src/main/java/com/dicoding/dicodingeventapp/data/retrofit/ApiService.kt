package com.dicoding.dicodingeventapp.data.retrofit

import com.dicoding.dicodingeventapp.data.response.DetailEventResponse
import com.dicoding.dicodingeventapp.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int,
        @Query("limit") limit: Int = 40
    ): Call<EventResponse>

    @GET("events")
    fun searchEvent(
        @Query("active") active: Int,
        @Query("q") q: String = ""
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailEventResponse>
}