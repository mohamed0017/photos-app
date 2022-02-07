package com.task.koinztask.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.task.koinztask.data.local.AppDatabase
import com.task.koinztask.data.mapper.PhotoMapper
import com.task.koinztask.data.remote.PhotosApi
import com.task.koinztask.data.repos.GetPhotosRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single { provideLoggingInterceptor() }
    single { provideHttpClient(loggingInterceptor = get())}
    single { providesGson() }
    single { provideRetrofit(baseUrl = get(named("BaseUrl")), gson = get(), okHttpClient = get()) }
    single { provideRoom(androidContext()) }
    single { providePhotosApi(get()) }
    single (named("BaseUrl")) { "https://www.flickr.com/" }

    single { GetPhotosRepository(photosApi = get(), appDatabase = get(), photoMapper = get()) }

    single { PhotoMapper() }
}
private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
}

private fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
    .addNetworkInterceptor(loggingInterceptor)
    .readTimeout(5, TimeUnit.MINUTES)
    .writeTimeout(5, TimeUnit.MINUTES)
    .build()

private fun providesGson(): Gson =
    GsonBuilder()
        .setLenient()
        .setDateFormat("MM/dd/yyyy")
        .create()

private fun provideRetrofit(baseUrl: String, gson: Gson, okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

private fun providePhotosApi(retrofit: Retrofit) = retrofit.create(PhotosApi::class.java)

private fun provideRoom(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "app-database")
        .build()

