package com.example.firebasesocial.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.firebasesocial.database.Post
import com.example.firebasesocial.database.PostRoomDao

class ViewViewModel(
    val database: PostRoomDao,
    application: Application
) : AndroidViewModel(application) {


    private val _allPost=database.getItems()

    val allPost: LiveData<List<Post>>
        get() {
            return _allPost
        }


}