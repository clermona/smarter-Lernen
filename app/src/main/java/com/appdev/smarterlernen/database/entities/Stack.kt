package com.appdev.smarterlernen.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "stack")
data class Stack (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @Relation(parentColumn = "id", entityColumn = "stackId") val cardList: List<Card>
)