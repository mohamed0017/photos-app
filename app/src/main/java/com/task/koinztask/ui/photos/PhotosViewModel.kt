package com.task.koinztask.ui.photos

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.task.koinztask.data.local.PhotoEntity
import com.task.koinztask.data.mapper.PhotoMapper
import com.task.koinztask.data.repos.GetPhotosRepository

class PhotosViewModel(
    private val photosRepository: GetPhotosRepository
) : ViewModel() {

    var photosLiveData: LiveData<PagingData<PhotoVM>> = loadPhotos()

    private fun loadPhotos() = photosRepository.loadPhotos().asLiveData().cachedIn(viewModelScope).map {
        it.map { mapToPhotoVM(it)}
    }

     private fun mapToPhotoVM(d: PhotoEntity): PhotoVM {
        return PhotoVM(imageUrl = "https://farm${d.farm}.static.flickr.com/${d.server}/${d.id}_${d.secret}.jpg")
    }
}