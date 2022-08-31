package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.repository.storage.dao.CategoryDao
import com.gholem.moneylab.repository.storage.dao.TransactionDao
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import javax.inject.Inject

class TransactionStorageRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : TransactionStorageRepository {

    override suspend fun insert(transactions: List<Transaction>) =
        transactionDao.insert(transactions.map { TransactionEntity.from(it) })

    override suspend fun getAll(): List<Transaction> {
        val transactions = transactionDao.getAll()

        return transactions.map { transaction ->
            val category = categoryDao.getById(transaction.categoryId)
            transaction.map(category)
        }
    }
}
