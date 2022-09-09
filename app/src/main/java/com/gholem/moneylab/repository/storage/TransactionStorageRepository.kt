package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionModel

interface TransactionStorageRepository {

    suspend fun insertList(transactions: List<TransactionModel>)
    suspend fun insertItem(transactions: TransactionModel)
    suspend fun getAll(): List<TransactionModel>
}