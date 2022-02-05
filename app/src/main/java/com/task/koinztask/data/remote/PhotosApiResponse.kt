package com.task.koinztask.data.remote

data class PhotosApiResponse(
    val stat: String?,
    val photos: PhotosDataResponse?,
    val message: String?,
    val code: String?
)

data class PhotosDataResponse(
    val page : Int?,
    val pages : Int?,
    val perpage : Int?,
    val total : Int?,
    val photo : List<Photo>?
)

data class Photo(
    val id : String,
    val owner : String?,
    val secret : String?,
    val server : String?,
    val title : String?,
    val farm : Int?,
    val ispublic : Int?,
    val isfriend : Int?,
    val isfamily : Int?,
)
