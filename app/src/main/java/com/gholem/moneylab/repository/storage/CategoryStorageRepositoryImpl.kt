package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.repository.storage.dao.CategoryDao
import com.gholem.moneylab.repository.storage.entity.CategoryEntity
import javax.inject.Inject

class CategoryStorageRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryStorageRepository {

    override suspend fun insert(category: TransactionCategoryModel): Long {
        return categoryDao.insert(CategoryEntity.from(category))
    }

    override suspend fun insert(categories: List<TransactionCategoryModel>) {
        return categoryDao.insert(categories.map { CategoryEntity.from(it) })
    }

    override suspend fun getAll(): List<TransactionCategoryModel> =
        categoryDao.getAll().map { it.toModel() }
}