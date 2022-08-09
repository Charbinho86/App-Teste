package com.example.appteste.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appteste.data.entity.AppTesteEntity

@Dao
interface AppTesteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appTesteEntity: AppTesteEntity): Long

    @Update
    suspend fun update(appTesteEntity: AppTesteEntity)

    @Query("DELETE FROM appTeste WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM appTeste")
    suspend fun deleteAll()

    @Query("SELECT * FROM appTeste WHERE id = :id")
    suspend fun get(id: Long): List<AppTesteEntity>?

    @Query("SELECT * FROM appTeste")
    suspend fun getAll(): List<AppTesteEntity>?
}