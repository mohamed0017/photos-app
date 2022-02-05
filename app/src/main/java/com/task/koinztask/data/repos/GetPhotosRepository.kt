package com.task.koinztask.data.repos

import com.task.koinztask.data.remote.PhotosApi
import com.task.koinztask.data.remote.PhotosApiResponse

interface PhotosDataSource{
    suspend fun getPhotos(page : String) : Result<PhotosApiResponse>
}

class GetPhotosRepository(private val photosApi: PhotosApi ) : PhotosDataSource {

    override suspend fun getPhotos(page : String): Result<PhotosApiResponse> {
        val response = photosApi.getPhotos(page)
        return if (response.isSuccessful) {
            if (response.body()?.stat == "ok")
                Result.Success(response.body())
            else
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


class ServerError(message: String? = null) : Throwable()
