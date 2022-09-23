package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.dao.CategoryDao
import com.gholem.moneylab.repository.storage.dao.TransactionDao
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import javax.inject.Inject

class TransactionStorageRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : TransactionStorageRepository {

    override suspend fun insertList(transactions: List<TransactionModel>) =
        transactionDao.insertList(transactions.map { TransactionEntity.from(it) })

    override suspend fun updateItem(transactions: TransactionModel) {
        transactionDao.update(TransactionEntity.setTransactionEntityId(transactions))
    }

    override suspend fun deleteItem(id: Int) {
        transactionDao.delete(id)
    }

    override suspend fun insertItem(transaction: TransactionModel) {
        transactionDao.insertItem(TransactionEntity.from(transaction))
    }

    override suspend fun getItemById(id: Long): TransactionModel {
        val item = transactionDao.getItem(id)
        return item.map(categoryDao.getById(item.categoryId))
    }

    override suspend fun getAll(): List<TransactionModel> {

        val transactions = transactionDao.getAll()

        return transactions.map { transaction ->
            val category = categoryDao.getById(transaction.categoryId)
            transaction.map(category)
        }
    }
}
