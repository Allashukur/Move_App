package com.example.moveapp.models.room_data_base.app_data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moveapp.models.room_data_base.dao.Move_dao
import com.example.moveapp.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moveapp.models.room_data_base.entity.MovePopularEntity

@Database(entities = [MovePopularEntity::class,MoveNewPlayingEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moveDao(): Move_dao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "MoveApp")
                    .allowMainThreadQueries().build()
            }
            return instance!!
        }

    }

}