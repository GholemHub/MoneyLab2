package com.gholem.moneylab.repository.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gholem.moneylab.repository.storage.entity.CategoryEntity
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(categoryEntity: CategoryEntity): Long

    @Query("DELETE FROM category_table WHERE id = :id")
    suspend fun deleteItem(id: Int)

    @Insert
    suspend fun insert(categoryEntities: List<CategoryEntity>)

    @Query("SELECT * FROM category_table")
    suspend fun getAll(): List<CategoryEntity>

    @Query("SELECT * FROM category_table WHERE id=:id")
    suspend fun getById(id: Long): CategoryEntity

    @Update
    suspend fun update(transactionEntity: CategoryEntity)

}