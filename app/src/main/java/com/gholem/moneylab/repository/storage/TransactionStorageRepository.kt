package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.Transaction

interface TransactionStorageRepository {

    suspend fun insert(transactions: List<Transaction>)

    suspend fun getAll(): List<Transaction>
}