package com.dicoding.dicodingeventapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingeventapp.data.EventRepository
import com.dicoding.dicodingeventapp.data.local.entity.FavoriteEvent

class MainViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _upcomingEvent = MutableLiveData<List<FavoriteEvent>>()
    val upcomingEvent: LiveData<List<FavoriteEvent>> = _upcomingEvent

    private val _finishedEvent = MutableLiveData<List<FavoriteEvent>>()
    val finishedEvent: LiveData<List<FavoriteEvent>> = _finishedEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming: LiveData<Boolean> = _isLoadingUpcoming

    private val _isLoadingFinished = MutableLiveData<Boolean>()
    val isLoadingFinished: LiveData<Boolean> = _isLoadingFinished


    fun getUpcomingEvents() = eventRepository.getUpcomingEvents()

    fun getFinishedEvents() = eventRepository.getFinishedEvents()

    fun getFavoriteEvents() = eventRepository.getFavoriteEventById("")

}