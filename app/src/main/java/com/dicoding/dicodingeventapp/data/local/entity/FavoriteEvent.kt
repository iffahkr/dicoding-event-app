package com.dicoding.dicodingeventapp.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String = "",
    var mediaCover: String? = null,
    var summary: String? = null,
    var isFavorite: Boolean = false,
    var active: Int = 0,
    var ownerName: String? = null,
    var quota: Int = 0,
    var registrants: Int = 0,
    var beginTime: String? = null,
    var link: String? = null,
    var description: String? = null
) : Parcelable
