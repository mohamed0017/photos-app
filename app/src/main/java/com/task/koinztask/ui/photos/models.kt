package com.task.koinztask.ui.photos

data class PhotoVM(
    val imageUrl: String? = null
)


sealed class UiModel {
    data class PhotoModel(val photoVM: PhotoVM, val index : Int) : UiModel()
    data class AdBannerModel(val adImageUrl: String) : UiModel()
}