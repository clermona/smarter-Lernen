package com.appdev.smarterlernen.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stack (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
)