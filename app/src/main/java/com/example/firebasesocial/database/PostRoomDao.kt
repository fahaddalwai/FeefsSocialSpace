package com.example.firebasesocial.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Query("select * from Post")
    fun getItems(): LiveData<List<Post>>

}