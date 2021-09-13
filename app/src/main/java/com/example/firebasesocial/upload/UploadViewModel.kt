package com.example.firebasesocial.upload

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasesocial.database.Post
import com.example.firebasesocial.database.PostRoomDao
import com.example.firebasesocial.repository.Repository
import kotlinx.coroutines.launch

class UploadViewModel(dataSource: PostRoomDao) : ViewModel() {



    val repository = Repository(dataSource)

    var postCaption = MutableLiveData<String>()

    var photo = MutableLiveData<Uri>()


    val _currentPostValue=repository.currentPost
    val currentPostValue: LiveData<Post>
        get() = _currentPostValue

    fun setCurrentPostValueToFalse(){
        _currentPostValue.value=null
    }



    private val _buttonClicked = MutableLiveData<Boolean>()
    val buttonClicked: LiveData<Boolean>
        get() = _buttonClicked

    fun setButtonToFalse() {
        _buttonClicked.value = false
    }

    fun setButtonToTrue() {
        _buttonClicked.value = true
    }


    private val _showToast = MutableLiveData<Boolean>()
    val showToast: LiveData<Boolean>
        get() = _showToast

    fun showToastTrue() {
        _showToast.value = true
    }

    fun showToastFalse() {
        _showToast.value = false
    }


    init {
        setButtonToFalse()
        showToastFalse()
        setCurrentPostValueToFalse()
    }

    fun uploadImage() {
        if (repository.uploadImage(photo.value!!, postCaption.value!!)) {
            showToastTrue()

        }
    }




        fun uploadToDatabase() {
            viewModelScope.launch {
                try {
                        repository.uploadToDatabase (repository.currentPost.value!!)

                } catch (e: Exception) {
                    Log.i("error buddy", e.toString())
                }
            }
        }


}