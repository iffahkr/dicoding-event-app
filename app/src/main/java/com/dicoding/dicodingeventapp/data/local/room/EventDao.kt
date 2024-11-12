package com.dicoding.dicodingeventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room. *
import com.dicoding.dicodingeventapp.data.local.entity.FavoriteEvent

@Dao
interface EventDao {

    @Query("SELECT * FROM FavoriteEvent WHERE active = :active ORDER BY id DESC")
    fun getAllEvent(active: Int): LiveData<List<FavoriteEvent>>

    @Query("SELECT * FROM FavoriteEvent WHERE id = :id")
    fun getDetailEvent(id: String): LiveData<FavoriteEvent>

    @Query("SELECT * FROM FavoriteEvent WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(event: List<FavoriteEvent>)

    @Query("DELETE FROM FavoriteEvent WHERE FavoriteEvent.id = :id")
    suspend fun deleteEvent(id: String)

    @Update
    suspend fun updateEvent(event: FavoriteEvent)

    @Query("SELECT EXISTS(SELECT * FROM FavoriteEvent WHERE FavoriteEvent.id = :id AND FavoriteEvent.isFavorite = 1)")
    suspend fun isEventFavorite(id: String): Boolean

}