package com.task.koinztask.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface PhotosApi {

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=50&text=Color&page={page}&per_page=20&api_key=308188167a9b120cff86caac1647331b")
    suspend fun getPhotos(@Path("page") page : String): Response<PhotosApiResponse>

}