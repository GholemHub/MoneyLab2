package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionModel

interface TransactionStorageRepository {

    suspend fun insertTransactionModel(transactionModel:TransactionModel)

    suspend fun getAll(): List<TransactionModel>
}