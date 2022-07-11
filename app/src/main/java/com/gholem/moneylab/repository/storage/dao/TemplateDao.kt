package com.gholem.moneylab.repository.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gholem.moneylab.repository.storage.entity.TemplateEntity

@Dao
interface TemplateDao {

    @Insert
    suspend fun insert(templateEntity: TemplateEntity)

    @Query("SELECT * FROM TemplateEntity")
    suspend fun getAll(): List<TemplateEntity>
}