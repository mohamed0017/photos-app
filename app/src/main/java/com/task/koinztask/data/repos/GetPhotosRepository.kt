package com.task.koinztask.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.*
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.task.koinztask.data.local.AppDatabase
import com.task.koinztask.data.local.PhotoEntity
import com.task.koinztask.data.mapper.PhotoMapper
import com.task.koinztask.data.meditator.PhotosMediator
import com.task.koinztask.data.remote.PhotosApi
import kotlinx.coroutines.flow.Flow


const val PAGE_SIZE = 20

interface PhotosDataSource {
    fun loadPhotos(): Flow<PagingData<PhotoEntity>>
}

class GetPhotosRepository(
    private val photosApi: PhotosApi,
    private val appDatabase: AppDatabase,
    private val photoMapper: PhotoMapper
) : PhotosDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun loadPhotos(): Flow<PagingData<PhotoEntity>> {
        val pagingSourceFactory = { appDatabase.photosDao().getAll() }

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false,initialLoadSize = PAGE_SIZE),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = PhotosMediator(photosApi, appDatabase, photoMapper)
        ).flow
    }
}