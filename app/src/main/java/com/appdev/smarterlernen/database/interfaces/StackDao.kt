package com.appdev.smarterlernen.database.interfaces

import androidx.room.*
import com.appdev.smarterlernen.database.entities.Stack

@Dao
interface StackDao {

    @Query("SELECT * FROM stack")
    fun getAll(): List<Stack>

    @Query("SELECT * from stack where id = :id LIMIT 1")
    fun getById(id: Int): Stack

    @Update
    fun update(stack: Stack)

    @Insert
    fun insert(vararg stack: Stack)

    @Delete
    fun delete(stack: Stack)

}