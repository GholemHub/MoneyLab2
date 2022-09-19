package com.gholem.moneylab.repository.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertList(transactionEntity: List<TransactionEntity>)

    @Insert
    suspend fun insertItem(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM transaction_table")
    suspend fun getAll(): List<TransactionEntity>

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAllData()

}