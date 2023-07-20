package com.appdev.smarterlernen.database.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "stack")
data class Stack(
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "rating") var rating:Int,
    @PrimaryKey var id: Int = (0..65535).random()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stack> {
        override fun createFromParcel(parcel: Parcel): Stack {
            return Stack(parcel)
        }

        override fun newArray(size: Int): Array<Stack?> {
            return arrayOfNulls(size)
        }
    }
}