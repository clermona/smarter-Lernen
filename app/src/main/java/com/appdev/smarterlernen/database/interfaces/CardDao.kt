package com.appdev.smarterlernen.database.interfaces

import androidx.room.*
import com.appdev.smarterlernen.database.entities.Card

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    fun getAll(): List<Card>

    @Query("SELECT * from card WHERE stack_id = :id")
    fun getByStackId(id: Int): List<Card>

    @Query("SELECT * FROM card WHERE id = :id")
    fun getById(id: Int): Card

    @Query("SELECT count(*) from card WHERE stack_id = :id")
    fun getCountByStackId(id: Int): Int

    @Query("SELECT count(*) from card where rating = 1")
    fun getCountEasyCards() : Int

    @Update
    fun update(card: Card)

    @Insert
    fun insert(vararg card: Card)

    @Delete
    fun delete(card: Card)

}