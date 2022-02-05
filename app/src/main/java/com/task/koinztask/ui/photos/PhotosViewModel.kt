package com.task.koinztask.ui.photos

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.task.koinztask.data.local.PhotoEntity
import com.task.koinztask.data.repos.GetPhotosRepository

class PhotosViewModel(private val photosRepository: GetPhotosRepository) : ViewModel() {

    var photosLiveData: LiveData<PagingData<PhotoEntity>> = loadPhotos()

    fun loadPhotos() = photosRepository.loadPhotos().asLiveData().cachedIn(viewModelScope)

}