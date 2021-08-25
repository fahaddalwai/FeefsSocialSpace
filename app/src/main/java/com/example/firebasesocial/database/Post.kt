package com.example.firebasesocial.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//the data to be put into the database
@Entity
data class Post(
    @PrimaryKey(autoGenerate = false)
    var ImageUrl: String="default" ,

    @ColumnInfo(name = "Caption")
    var Caption:String?="default"
)