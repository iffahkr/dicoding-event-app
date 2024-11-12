package com.dicoding.dicodingeventapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dicoding.dicodingeventapp.data.local.entity.FavoriteEvent
import com.dicoding.dicodingeventapp.data.local.room.EventDao
import com.dicoding.dicodingeventapp.data.remote.retrofit.ApiService

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
) {

    fun getUpcomingEvents(): LiveData<Result<List<FavoriteEvent>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getEvents(active = 1.toString())
            val events = response.listEvents
            val eventList = events.map { event ->
                val isFavorite = eventDao.isEventFavorite(event.id.toString())
                FavoriteEvent(
                    event.id.toString(),
                    event.name,
                    event.mediaCover,
                    event.summary,
                    isFavorite,
                    active = 1
                )

            }
            eventDao.deleteEvent("")
            eventDao.insertEvent(eventList)
        } catch (e: Exception) {
            Log.d("EventRepository", "getUpcomingEvents: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<FavoriteEvent>>> =
            eventDao.getAllEvent(1).map { Result.Success(it) }
        emitSource(localData)
    }

    fun getFinishedEvents(): LiveData<Result<List<FavoriteEvent>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getEvents(active = 0.toString())
            val events = response.listEvents
            val eventList = events.map { event ->
                val isFavorite = eventDao.isEventFavorite(event.id.toString())
                FavoriteEvent(
                    event.id.toString(),
                    event.name,
                    event.mediaCover,
                    event.summary,
                    isFavorite,
                    active = 0
                )
            }
            eventDao.deleteEvent("")
            eventDao.insertEvent(eventList)
        } catch (e: Exception) {
            Log.d("EventRepository", "getFinishedEvents: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<FavoriteEvent>>> =
            eventDao.getAllEvent(0).map { Result.Success(it) }
        emitSource(localData)
    }

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> {
        return eventDao.getFavoriteEventById(id)
    }

    suspend fun setFavoriteEvent(event: FavoriteEvent, favoriteState: Boolean) {
        event.isFavorite = favoriteState
        eventDao.updateEvent(event)
    }


    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }
}