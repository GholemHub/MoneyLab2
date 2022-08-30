package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

interface TransactionStorageRepository {
    suspend fun insert(transactions: List<Transaction>)
    suspend fun getAll(): List<TransactionEntity>
}