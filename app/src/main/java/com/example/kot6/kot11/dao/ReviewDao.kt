package com.example.kot6.kot11.dao

import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kot6.kot11.model.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review WHERE id == :id")
    fun getOneReview(id:Int):Review

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReview(review: Review)
}
