package com.dicoding.dicodingeventapp.data.remote.retrofit

import com.dicoding.dicodingeventapp.data.remote.response.DetailEventResponse
import com.dicoding.dicodingeventapp.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: String
    ): EventResponse

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailEventResponse>
}