package com.gholem.moneylab.repository.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transactionEntity: List<TransactionEntity>)

    @Update
    suspend fun update(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM transaction_table")
    suspend fun getAll(): List<TransactionEntity>

    @Query("DELETE  FROM transaction_table")
    suspend fun deleteAllData()
    
}