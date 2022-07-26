package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.repository.storage.dao.TransactionDao
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import javax.inject.Inject

class TransactionStorageRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionStorageRepository {
    override suspend fun insertTransactionModel(transaction: Transaction) =
        transactionDao.insert(TransactionEntity.from(transaction))

    override suspend fun getAll(): List<Transaction> =
        transactionDao.getAll().map { it.toModel() }
}
