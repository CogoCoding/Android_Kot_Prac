package com.example.kot6.kot11.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kot6.kot11.dao.HistoryDao

@Database(entities = [History::class],version=1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun historyDao(): HistoryDao
}