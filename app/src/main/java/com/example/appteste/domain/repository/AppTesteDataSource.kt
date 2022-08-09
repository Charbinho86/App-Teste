package com.example.appteste.domain.repository

import com.example.appteste.data.dao.AppTesteDao
import com.example.appteste.data.entity.AppTesteEntity

class AppTesteDataSource(
    private val appTesteDao: AppTesteDao
) : AppTesteRepository {

    override suspend fun insert(name: String, email: String, telefone: String): Long {
        val appteste = AppTesteEntity(
            nome = name,
            email = email,
            telefone = telefone
        )
        return appTesteDao.insert(appteste)
    }

    override suspend fun update(id: Long, name: String, email: String, telefone: String) {
        val appteste = AppTesteEntity(
            id = id,
            nome = name,
            email = email,
            telefone = telefone
        )
        return appTesteDao.update(appteste)
    }

    override suspend fun delete(id: Long) {
        appTesteDao.delete(id)
    }

    override suspend fun deleteAll() {
        appTesteDao.deleteAll()
    }

    override suspend fun get(id: Long): List<AppTesteEntity>? {
        return appTesteDao.get(id)
    }

    override suspend fun getAll(): List<AppTesteEntity>? {
        return appTesteDao.getAll()
    }

}