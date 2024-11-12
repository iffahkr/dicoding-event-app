package com.dicoding.dicodingeventapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingeventapp.data.EventRepository
import com.dicoding.dicodingeventapp.data.local.entity.FavoriteEvent
import com.dicoding.dicodingeventapp.data.remote.response.DetailEventResponse
import com.dicoding.dicodingeventapp.data.remote.response.Event
import com.dicoding.dicodingeventapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _detailEvent = MutableLiveData<Event?>()
    val detailEvent: LiveData<Event?> = _detailEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailEvent(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailEvent(id)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val event = response.body()?.event
                    _detailEvent.value = event
                    event?.let { checkIfFavorite(it.id.toString()) }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun checkIfFavorite(id: String) {
        viewModelScope.launch {
            _isFavorite.value = eventRepository.getFavoriteEventById(id).value != null
        }
    }

    fun toggleFavorite(event: Event) {
        val favoriteEvent = event.toFavoriteEvent()
        viewModelScope.launch {
            val isFavorite = eventRepository.getFavoriteEventById(favoriteEvent.id).value != null
            if (isFavorite) {
                deleteEvent(favoriteEvent)
            } else {
                saveEvent(favoriteEvent)
            }
        }
        // update _isFavorite reflect the new status
        _isFavorite.value = !isFavorite.value!!
    }

    private fun Event.toFavoriteEvent(): FavoriteEvent {
        return FavoriteEvent(
            id = this.id.toString(),
            name = this.name,
            mediaCover = this.mediaCover,
            summary = this.summary,
            beginTime = this.beginTime,
            ownerName = this.ownerName,
            description = this.description,
            quota = this.quota,
            registrants = this.registrants,
            isFavorite = false
        )
    }

    private fun saveEvent(event: FavoriteEvent) {
        viewModelScope.launch {
            eventRepository.setFavoriteEvent(event, true)
        }
    }

    private fun deleteEvent(event: FavoriteEvent) {
        viewModelScope.launch {
            eventRepository.setFavoriteEvent(event, false)
        }
    }
}
