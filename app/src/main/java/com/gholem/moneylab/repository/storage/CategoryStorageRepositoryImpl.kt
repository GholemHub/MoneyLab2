package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.repository.storage.dao.CategoryDao
import com.gholem.moneylab.repository.storage.entity.CategoryEntity
import javax.inject.Inject

class CategoryStorageRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryStorageRepository {

    override suspend fun insert(category: CategoryItem): Long {
        return categoryDao.insert(CategoryEntity.from(category))
    }

    override suspend fun insertList(categories: List<CategoryItem>) {
        return categoryDao.insertList(categories.map { CategoryEntity.from(it) })
    }

    override suspend fun getAll(): List<CategoryItem> =
        categoryDao.getAll().map { it.toModel() }

    override suspend fun deleteItem(category: Int) {
        categoryDao.deleteItem(category)
    }

    override suspend fun updateItem(category: CategoryItem) {
        categoryDao.update(CategoryEntity.from(category))
    }
}