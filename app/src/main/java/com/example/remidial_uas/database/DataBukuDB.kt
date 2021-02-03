package com.example.remidial_uas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Buku::class), version = 1, exportSchema = false)
abstract class DataBukuDB : RoomDatabase() {

    abstract fun bukuDao(): BukuDao


    companion object {
        @Volatile
        private var INSTANCE: DataBukuDB? = null

        fun getDatabase(context: Context): DataBukuDB {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    DataBukuDB::class.java,
                    "buku.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}