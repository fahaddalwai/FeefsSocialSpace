package com.example.firebasesocial.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 4)
abstract class PostRoomDatabase : RoomDatabase() {

    abstract val postRoomDao: PostRoomDao

    companion object {           //companion object is used to  access the methods for creating or getting the database without instantiating the class

        @Volatile                  //A volatile variable will never be cached, and all writes and reads will be done to and from the main memory
        private var INSTANCE: PostRoomDatabase? =null       //keep a reference to the database, when one has been created

        fun getInstance(context: Context): PostRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance==null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostRoomDatabase::class.java,
                        "Post_Database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}