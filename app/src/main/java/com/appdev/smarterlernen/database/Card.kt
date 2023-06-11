package com.appdev.smarterlernen.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "front_side") val frontSide: String?,
    @ColumnInfo(name = "back_side") val backSide: String?,
    @ColumnInfo(name = "tag") val tag: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "ghost_status") val ghostStatus: Boolean?,
)