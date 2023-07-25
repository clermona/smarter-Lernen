package com.appdev.smarterlernen.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao

@Database(entities = [Card::class, Stack::class], version = 7)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cardDao(): CardDao
    abstract fun stackDao(): StackDao

    // Singleton
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "smarter_lernen_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}