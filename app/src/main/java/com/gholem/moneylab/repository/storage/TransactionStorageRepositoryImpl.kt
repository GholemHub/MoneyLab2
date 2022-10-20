package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.dao.CategoryDao
import com.gholem.moneylab.repository.storage.dao.TransactionDao
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import timber.log.Timber.i
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
        var categoryDaoId = categoryDao.getById(item.categoryId)
        if(categoryDaoId == null){
            categoryDaoId = categoryDao.getById(categoryDao.getAll().first().id)
        }else{
            categoryDaoId = categoryDao.getById(item.categoryId)
        }
        return item.map(categoryDaoId)
    }

    override suspend fun getAll(): List<TransactionModel> {

        val transactions = transactionDao.getAll()

        return transactions.map { transaction ->
            val categoryListDao = categoryDao.getAll()
            val itemWithIdOfCategory = categoryListDao.firstOrNull {
                it.id == transaction.categoryId
            }
            if (itemWithIdOfCategory != null) {
                val category = categoryDao.getById(transaction.categoryId)
                transaction.map(category)
            } else {
                val category = categoryDao.getById(categoryListDao.first().id)
                transaction.map(category)
            }
        }
    }
}
