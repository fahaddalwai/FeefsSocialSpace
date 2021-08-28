package com.example.firebasesocial.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.firebasesocial.database.Post
import com.example.firebasesocial.database.PostRoomDao
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class Repository(private val dao: PostRoomDao) {

    var currentPost= MutableLiveData<Post>()

    fun uploadImage(photo: Uri, caption:String): Boolean {

        val storage: StorageReference = FirebaseStorage.getInstance().reference
        val databaseReference = FirebaseDatabase.getInstance().getReference("images")



        var checker=true
        val randomUUID=UUID.randomUUID().toString()
        val fileRef: StorageReference = storage.child("images/*$randomUUID")

        fileRef.putFile(photo)
            .addOnSuccessListener { taskSnapshot ->
                val urlTask = taskSnapshot.storage.downloadUrl
                while (!urlTask.isSuccessful);
                val downloadUrl = urlTask.result.toString()
                val uploadId: String? = databaseReference.push().key
                if (uploadId != null) {
                    currentPost.value=Post(downloadUrl,caption)
                    databaseReference.child(uploadId).setValue(currentPost)
                }
            }
            .addOnFailureListener {
                checker = false
            }
        return checker
    }



    suspend fun uploadToDatabase(post: Post) {
        dao.insertPost(post)
    }


}