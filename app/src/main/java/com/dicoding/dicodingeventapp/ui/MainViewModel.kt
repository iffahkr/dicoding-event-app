package com.dicoding.dicodingeventapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingeventapp.data.response.DetailEventResponse
import com.dicoding.dicodingeventapp.data.response.Event
import com.dicoding.dicodingeventapp.data.response.EventResponse
import com.dicoding.dicodingeventapp.data.response.ListEventsItem
import com.dicoding.dicodingeventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _upcomingEvent = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvent: LiveData<List<ListEventsItem>> = _upcomingEvent

    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming: LiveData<Boolean> = _isLoadingUpcoming

    private val _isLoadingFinished = MutableLiveData<Boolean>()
    val isLoadingFinished: LiveData<Boolean> = _isLoadingFinished

    private val _searchEvent = MutableLiveData<EventResponse>()
    val searchEvent: LiveData<EventResponse> = _searchEvent

    private val _detailEvent = MutableLiveData<Event>()
    val detailEvent: LiveData<Event> = _detailEvent

    companion object {
        private const val TAG = "MainViewModel"
    }
    init {
        getDetailEvent()
        getUpcomingEvents()
        getFinishedEvents()
    }

    private fun getDetailEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailEvent("")
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                if (response.isSuccessful) {
                    _detailEvent.value = response.body()?.event
                }
            }
            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    private fun getUpcomingEvents() {
        _isLoadingUpcoming.value = true
        val client = ApiConfig.getApiService().getEvents(active = 1)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoadingUpcoming.value = false
                if (response.isSuccessful) {
                    _upcomingEvent.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingUpcoming.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getFinishedEvents() {
        _isLoadingFinished.value = true
        val client = ApiConfig.getApiService().getEvents(active = 0)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoadingFinished.value = false
                if (response.isSuccessful) {
                    _finishedEvent.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingFinished.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}