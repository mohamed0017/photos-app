package com.task.koinztask.data.local

import com.task.koinztask.data.remote.PhotosDataResponse

interface PhotosCacheDataSource {
    suspend fun savePhotos(photosApiResponse: PhotosDataResponse)
}

class PhotosCache(private val appDatabase: AppDatabase) : PhotosCacheDataSource {
    override suspend fun savePhotos(photosApiResponse: PhotosDataResponse) {
        appDatabase.photosDao().deleteAll()
        appDatabase.photosDao().insertAll(photosApiResponse.photo?.map {
            PhotoEntity(
                id = it.id,
                server = it.server,
                secret = it.secret,
                isFriend = it.isfriend,
                isFamily = it.isfamily,
                isPublic = it.ispublic,
                title = it.title,
                farm = it.farm,
                owner = it.owner
            )
        }!!)
    }


}