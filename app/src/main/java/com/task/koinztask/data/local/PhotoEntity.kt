package com.task.koinztask.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "owner") val owner: String?,
    @ColumnInfo(name = "secret") val secret: String?,
    @ColumnInfo(name = "server") val server: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "farm") val farm: Int?,
    @ColumnInfo(name = "ispublic") val isPublic: Int?,
    @ColumnInfo(name = "isfriend") val isFriend: Int?,
    @ColumnInfo(name = "isfamily") val isFamily: Int?,
)