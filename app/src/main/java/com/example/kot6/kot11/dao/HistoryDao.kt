package com.example.kot6.kot11.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kot6.kot11.model.History

@Dao
interface HistoryDao {
    // 히스토리창 초기
    @Query("SELECT * FROM history")
    fun getAll():List<History>

    @Insert
    fun insertHistory(history:History)

    @Query("DELETE FROM history WHERE keyword == :keyword")
    fun delete(keyword:String)

    //검색작업 insert history
    // x누르면 키워드 하나 지워주는
}