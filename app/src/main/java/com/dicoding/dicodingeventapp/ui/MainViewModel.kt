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
        findEvent(0)
        findEvent(1)
    }


    private fun findEvent(active: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(active)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        when (active) {
                            1 -> _upcomingEvent.value = it
                            0 -> _finishedEvent.value = it
                        }
                    } ?: Log.e(TAG, "OnFailure: Response body is null")
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