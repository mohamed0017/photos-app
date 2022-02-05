package com.task.koinztask.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotosApi {

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=50&text=Color&api_key=308188167a9b120cff86caac1647331b")
    suspend fun getPhotos(@Query("page") page : Int, @Query("pageSize") pageSize : Int): Response<PhotosApiResponse>

}