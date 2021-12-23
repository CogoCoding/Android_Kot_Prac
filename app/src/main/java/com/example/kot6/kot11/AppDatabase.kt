package com.example.kot6.kot11

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kot6.kot11.dao.HistoryDao
import com.example.kot6.kot11.dao.ReviewDao
import com.example.kot6.kot11.model.History
import com.example.kot6.kot11.model.Review

@Database(entities = [History::class, Review::class],version=1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao

}