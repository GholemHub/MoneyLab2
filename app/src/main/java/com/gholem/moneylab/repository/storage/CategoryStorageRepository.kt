package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.CategoryItem

interface CategoryStorageRepository {

    suspend fun insert(category: CategoryItem): Long

    suspend fun insertList(categories: List<CategoryItem>)

    suspend fun getAll(): List<CategoryItem>

    suspend fun deleteItem(category: Int)

    suspend fun updateItem(category: CategoryItem)
}