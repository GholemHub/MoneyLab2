package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.repository.storage.dao.CategoryDao
import com.gholem.moneylab.repository.storage.entity.CategoryEntity
import javax.inject.Inject

class CategoryStorageRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryStorageRepository {

    override suspend fun insert(category: TransactionCategory): Long {
        return categoryDao.insert(CategoryEntity.from(category))
    }

    override suspend fun getAll(): List<TransactionCategory> =
        categoryDao.getAll().map { it.toModel() }
}