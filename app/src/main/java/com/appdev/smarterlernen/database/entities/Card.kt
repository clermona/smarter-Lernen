package com.appdev.smarterlernen.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "card",
    foreignKeys = [ForeignKey(entity = Stack::class, parentColumns = ["id"], childColumns = ["stack_id"])]
)
data class Card (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "stack_id") val stackId: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "front_side") val frontSide: String?,
    @ColumnInfo(name = "back_side") val backSide: String?,
    @ColumnInfo(name = "tag") val tag: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "ghost_status") val ghostStatus: Boolean?,
)