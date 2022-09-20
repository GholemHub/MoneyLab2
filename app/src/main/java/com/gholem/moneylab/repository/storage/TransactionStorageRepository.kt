package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionModel

interface TransactionStorageRepository {

    suspend fun updateItem(transactions: TransactionModel)
    suspend fun deleteItem(id: Int)
    suspend fun insertList(transactions: List<TransactionModel>)
    suspend fun insertItem(transactions: TransactionModel)
    suspend fun getAll(): List<TransactionModel>
}