package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.Transaction

interface TransactionStorageRepository {

    suspend fun insertTransactionModel(transaction:Transaction)

    suspend fun getAll(): List<Transaction>
}