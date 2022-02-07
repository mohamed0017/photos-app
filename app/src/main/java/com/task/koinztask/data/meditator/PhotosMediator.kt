package com.task.koinztask.data.meditator

import androidx.paging.*
import com.task.koinztask.data.local.AppDatabase
import com.task.koinztask.data.local.PhotoEntity
import com.task.koinztask.data.local.RemoteKeys
import com.task.koinztask.data.mapper.PhotoMapper
import com.task.koinztask.data.remote.PhotosApi
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

const val DEFAULT_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class PhotosMediator(
    private val photosService: PhotosApi,
    private val appDatabase: AppDatabase,
    private val photoMapper: PhotoMapper
) : RemoteMediator<Int, PhotoEntity>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, PhotoEntity>
    ): MediatorResult {

        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = photosService.getPhotos(page, state.config.pageSize)
            val isEndOfList = response.body()?.photos?.photo?.isEmpty()

            if (loadType == LoadType.REFRESH) {
                appDatabase.remoteKeysDao().clearRemoteKeys()
                appDatabase.photosDao().deleteAll()
            }

            val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
            val nextKey = if (isEndOfList == true) null else page + 1
            val keys = response.body()?.photos?.photo?.map {
                RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            keys?.let { appDatabase.remoteKeysDao().insertAll(it) }
            appDatabase.photosDao().insertAll(response.body()?.photos?.photo?.map {
                photoMapper.map(it)
            } ?: emptyList())

            return MediatorResult.Success(endOfPaginationReached = isEndOfList == true)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state) ?: return MediatorResult.Success(endOfPaginationReached = false)
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, PhotoEntity>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { photo -> appDatabase.remoteKeysDao().remoteKeysPhotoId(photo.id) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, PhotoEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                appDatabase.remoteKeysDao().remoteKeysPhotoId(repoId)
            }
        }
    }
}