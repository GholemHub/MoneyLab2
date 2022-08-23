package com.gholem.moneylab.repository.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gholem.moneylab.repository.storage.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(categoryEntity: CategoryEntity): Long

    @Query("SELECT * FROM category_table")
    suspend fun getAll(): List<CategoryEntity>
}