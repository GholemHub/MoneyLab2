package com.gholem.moneylab.repository.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM transaction_table")
    suspend fun getAll(): List<TransactionEntity>

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAllData()

}