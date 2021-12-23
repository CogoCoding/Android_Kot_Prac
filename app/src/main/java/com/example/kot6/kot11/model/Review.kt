package com.example.kot6.kot11.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity
data class Review (
    @PrimaryKey val id:Int?,
    @ColumnInfo(name = "review") val review: String?
)