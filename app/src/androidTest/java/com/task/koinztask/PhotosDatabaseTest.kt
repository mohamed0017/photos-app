package com.task.koinztask

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.koinztask.data.local.AppDatabase
import com.task.koinztask.data.local.PhotoEntity
import com.task.koinztask.data.local.PhotosDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class LanguageDatabaseTest : TestCase() {
    // get reference to the LanguageDatabase and LanguageDao class
    private lateinit var db: AppDatabase
    private lateinit var dao: PhotosDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.photosDao()
    }

    @After
    fun closeDb() {
        db.close()
    }
    
    @Test
    fun photoDaoInsertTest() = runBlocking<Unit> {
        val photos = listOf(PhotoEntity("photo", "me","00","00","00",0,0,0,0))
        dao.insertAll(photos)
        val photosFromDb = dao.getAll()

        assertEquals(photos, photosFromDb.getData())

    }

    @Test
    fun photoDaoDeleteTest() = runBlocking<Unit> {
        dao.deleteAll()
        val photosFromDb = dao.getAll()

        assert(photosFromDb.getData().isNullOrEmpty())

    }

}


