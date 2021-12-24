package com.example.kot6.kot11

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kot6.kot11.dao.HistoryDao
import com.example.kot6.kot11.dao.ReviewDao
import com.example.kot6.kot11.model.History
import com.example.kot6.kot11.model.Review

//Room은 핸드폰 하드웨어에 저장되니까 version을 지정해서
//version1은 처음 historyDao로 이미 파일 만들고, reviewDao할 때 있던 파일 지우고 다시 만드는걸 표시함

@Database(entities = [History::class, Review::class],version=1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}

fun getAppDatabase(context: Context):AppDatabase{
    val migration_1_2 = object : Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE 'REVIEW' ('id' INTEGER ,'review' TEXT"+"PRIMARY KEY('id'))")
        }
    }
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
    "BookSearchDb"
    ).addMigrations(migration_1_2)
        .build()
}
