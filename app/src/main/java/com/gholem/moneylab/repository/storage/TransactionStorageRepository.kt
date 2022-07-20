package com.gholem.moneylab.repository.storage

import androidx.lifecycle.LiveData
import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

interface TransactionStorageRepository {

    suspend fun insertTransactionModel(transactionModel: TransactionModel)

    suspend fun getAll(): LiveData<List<TransactionModel>>
}