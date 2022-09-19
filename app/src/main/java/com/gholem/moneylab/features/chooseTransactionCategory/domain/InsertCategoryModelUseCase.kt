package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class InsertCategoryModelUseCase @Inject constructor(
    private val categoryStorageRepository: CategoryStorageRepository
) : UseCase<TransactionCategoryModel, Long> {

    override suspend fun run(input: TransactionCategoryModel): Long =
        categoryStorageRepository.insert(input)
}