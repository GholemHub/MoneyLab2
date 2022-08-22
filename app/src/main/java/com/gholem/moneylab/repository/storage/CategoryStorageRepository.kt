package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionCategory

interface CategoryStorageRepository {
    suspend fun insert(category: TransactionCategory)
    suspend fun getAll(): List<TransactionCategory>
}