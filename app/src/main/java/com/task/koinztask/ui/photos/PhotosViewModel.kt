package com.task.koinztask.ui.photos

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.*
import androidx.paging.map
import com.task.koinztask.data.local.PhotoEntity
import com.task.koinztask.data.repos.GetPhotosRepository
import kotlinx.coroutines.flow.map

class PhotosViewModel(
    private val photosRepository: GetPhotosRepository
) : ViewModel() {

    var photosLiveData: LiveData<PagingData<UiModel>> = loadPhotos()
    var index = 0

    private fun loadPhotos() = photosRepository.loadPhotos().map { pagingData ->
        pagingData.map {
            UiModel.PhotoModel(mapToPhotoVM(it), ++index)
        }.insertSeparators { before, after ->
                when {
                    before == null -> null
                    before.index % 5 == 0 -> UiModel.AdBannerModel("https://farm66.static.flickr.com/65535/50397567507_855de8e6a9.jpg")
                    else -> null
                }
            }
    }.asLiveData().cachedIn(viewModelScope)

    private fun mapToPhotoVM(d: PhotoEntity): PhotoVM {
        return PhotoVM(imageUrl = "https://farm${d.farm}.static.flickr.com/${d.server}/${d.id}_${d.secret}.jpg")
    }
}