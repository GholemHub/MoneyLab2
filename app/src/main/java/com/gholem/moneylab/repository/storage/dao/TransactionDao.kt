package com.gholem.moneylab.repository.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM transaction_table")
    fun getAll(): LiveData<List<TransactionModel>>

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAllData()

}