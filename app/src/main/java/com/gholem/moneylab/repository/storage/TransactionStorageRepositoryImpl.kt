package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.dao.TransactionDao
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import javax.inject.Inject

class TransactionStorageRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionStorageRepository {
    //val readAllData: LiveData<List<TransactionModel>> = transactionDao.getAll()

    override suspend fun insertTransactionModel(transactionModel: TransactionModel) {
        transactionDao.insert(TransactionEntity.from(transactionModel))
    }

    override suspend fun getAll(): List<TransactionModel> =
        transactionDao.getAll()
}
