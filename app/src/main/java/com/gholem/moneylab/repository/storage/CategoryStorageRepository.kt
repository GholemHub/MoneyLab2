package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TransactionCategoryModel

interface CategoryStorageRepository {

    suspend fun insert(category: TransactionCategoryModel): Long

    suspend fun insert(categories: List<TransactionCategoryModel>)

    suspend fun getAll(): List<TransactionCategoryModel>

    suspend fun deleteItem(category: Int)

    suspend fun updateItem(category: TransactionCategoryModel)
}