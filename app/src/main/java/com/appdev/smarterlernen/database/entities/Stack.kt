package com.appdev.smarterlernen.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "stack")
data class Stack(
    @ColumnInfo(name = "title") var title: String?,
    @PrimaryKey var id: Int = (0..65535).random()
)