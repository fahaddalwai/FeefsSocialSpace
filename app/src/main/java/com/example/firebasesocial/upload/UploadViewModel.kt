package com.example.firebasesocial.upload

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasesocial.database.Post
import com.example.firebasesocial.database.PostRoomDao
import com.example.firebasesocial.repository.Repository
import kotlinx.coroutines.launch

class UploadViewModel(dataSource: PostRoomDao, application: Application) : ViewModel(){

    val repository=Repository(dataSource)

    var currentItem= MutableLiveData<Post>()

    var photo = MutableLiveData<Uri>()

    var caption = MutableLiveData<String>()

    var showtoast = MutableLiveData<Boolean>()






    init{
        showtoast.value = false

    }

    fun uploadImage(){

        if(photo.value?.let { repository.uploadImage(it, caption.value.toString()) } == true){
            showtoast.value=true
            currentItem.value=repository.currentPost
            uploadToDatabase(currentItem.value!!)
        }
    }

    fun uploadToDatabase(post:Post) {
        viewModelScope.launch {
            try{
                repository.uploadToDatabase(post)
            }catch (e:Exception){
                Log.i("error buddy",e.toString())
            }
        }
    }


}