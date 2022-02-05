package com.task.koinztask.data.local

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface PhotosDao {
    @Query("SELECT * FROM photos")
    fun getAll() : PagingSource<Int, PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( photos: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun deleteAll()
}