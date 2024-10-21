package com.dicoding.dicodingeventapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingeventapp.data.response.DetailEventResponse
import com.dicoding.dicodingeventapp.data.response.EventResponse
import com.dicoding.dicodingeventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _upcomingEvent = MutableLiveData<EventResponse>()
    val upcomingEvent: LiveData<EventResponse> = _upcomingEvent

    private val _finishedEvent = MutableLiveData<EventResponse>()
    val finishedEvent: LiveData<EventResponse> = _finishedEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchEvent = MutableLiveData<EventResponse>()
    val searchEvent: LiveData<EventResponse> = _searchEvent

    private val _detailEvent = MutableLiveData<DetailEventResponse>()
    val detailEvent: LiveData<DetailEventResponse> = _detailEvent

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        getUpcomingEvents()
        getFinishedEvents()
        getDetailEvent()
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
                    _detailEvent.value = response.body()
                }
            }
            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    private fun getUpcomingEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(active = 1)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _upcomingEvent.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getFinishedEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(active = 0)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _finishedEvent.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}