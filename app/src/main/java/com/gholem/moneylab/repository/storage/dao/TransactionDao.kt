package com.gholem.moneylab.repository.storage.dao

import androidx.room.*
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertList(transactionEntity: List<TransactionEntity>)

    @Insert
    suspend fun insertItem(transactionEntity: TransactionEntity)

    @Update
    suspend fun update(transactionEntity: TransactionEntity)

    @Query("DELETE FROM transaction_table WHERE id = :id")
    suspend fun del(id: Int)

    @Query("SELECT * FROM transaction_table")
    suspend fun getAll(): List<TransactionEntity>

    @Query("DELETE  FROM transaction_table")
    suspend fun deleteAllData()

}