package com.appdev.smarterlernen.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "card",
    foreignKeys = [ForeignKey(entity = Stack::class, parentColumns = ["id"], childColumns = ["stack_id"])]
)
data class Card  (

    @ColumnInfo(name = "stack_id") var stackId: Int,

    @ColumnInfo(name = "front_side") var frontSide: String? = "",
    @ColumnInfo(name = "back_side") var backSide: String? = "",
    @ColumnInfo(name = "rating") var rating: Int,
    @ColumnInfo(name = "tag") var tag: String? = "",
    @ColumnInfo(name = "status") var status: String? = "",
    @ColumnInfo(name = "ghost_status") var ghostStatus: Boolean? = false,
    @PrimaryKey var id: Int = (0..65535).random()
):Serializable