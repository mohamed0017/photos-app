package com.task.koinztask.ui.photos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.task.koinztask.R

class PhotosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photos_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PhotosFragment.newInstance())
                .commitNow()
        }
    }
}