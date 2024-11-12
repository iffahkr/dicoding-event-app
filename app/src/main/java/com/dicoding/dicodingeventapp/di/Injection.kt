package com.dicoding.dicodingeventapp.di

import android.content.Context
import com.dicoding.dicodingeventapp.data.EventRepository
import com.dicoding.dicodingeventapp.data.local.room.EventDatabase
import com.dicoding.dicodingeventapp.data.remote.retrofit.ApiConfig
import com.dicoding.dicodingeventapp.ui.setting.SettingPreferences
import com.dicoding.dicodingeventapp.ui.setting.dataStore

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return EventRepository.getInstance(apiService, dao)
    }

    fun provideSettingPreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.dataStore)
    }

}