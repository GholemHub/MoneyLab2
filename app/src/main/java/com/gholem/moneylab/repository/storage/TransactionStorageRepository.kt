package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionModel

interface TransactionStorageRepository {

    suspend fun insert(transactions: List<TransactionModel>)
    suspend fun getAll(): List<TransactionModel>
}