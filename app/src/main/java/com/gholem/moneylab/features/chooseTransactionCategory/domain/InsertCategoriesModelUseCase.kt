package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class InsertCategoriesModelUseCase @Inject constructor(
    private val categoryStorageRepository: CategoryStorageRepository
) : UseCase<List<TransactionCategoryModel>, Unit> {

    override suspend fun run(input: List<TransactionCategoryModel>): Unit =
        categoryStorageRepository.insert(input)
}