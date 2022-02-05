package com.task.koinztask.data.local

import androidx.room.*

@Dao
interface PhotosDao {
    @Query("SELECT * FROM photos")
    suspend fun getAll(): List<PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( photos: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun deleteAll()
}