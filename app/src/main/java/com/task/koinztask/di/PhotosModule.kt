package com.task.koinztask.di

import com.task.koinztask.ui.photos.PhotosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val photosModule = module{

    viewModel { PhotosViewModel(photosRepository = get()) }
}