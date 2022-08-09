package com.example.appteste.domain.repository

import com.example.appteste.data.entity.AppTesteEntity

interface AppTesteRepository {

    suspend fun insert(name: String, email: String, telefone: String): Long

    suspend fun update(id: Long, name: String, email: String, telefone: String)

    suspend fun delete(id: Long)

    suspend fun deleteAll()

    suspend fun get(id: Long): List<AppTesteEntity>?

    suspend fun getAll(): List<AppTesteEntity>?
}