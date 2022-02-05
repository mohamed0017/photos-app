package com.task.koinztask.data.repos

import com.task.koinztask.data.local.PhotosCache
import com.task.koinztask.data.remote.PhotosApi
import com.task.koinztask.data.remote.PhotosApiResponse
import com.task.koinztask.data.remote.PhotosDataResponse
import retrofit2.Response

interface PhotosDataSource {
    suspend fun getPhotos(page: String): Result<PhotosDataResponse>
}

class GetPhotosRepository(private val photosApi: PhotosApi, private val photosCache: PhotosCache) :
    PhotosDataSource {

    override suspend fun getPhotos(page: String): Result<PhotosDataResponse> {
        return handleApiResponse { photosApi.getPhotos(page) }
    }

    private suspend fun handleApiResponse(call: suspend () -> Response<PhotosApiResponse>): Result<PhotosDataResponse> {

        val response = call.invoke()
        return if (response.isSuccessful) {
            if (response.body()?.stat == "ok") {

                response.body()?.photos?.let { photosCache.savePhotos(it) }

                Result.Success(response.body()?.photos)
            } else
                Result.Error(ServerError(response.body()?.message))
        } else
            Result.Error(ServerError())
    }
}


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T?) : Result<T>()
    data class Error<out T>(val error: Throwable) : Result<T>()
}
