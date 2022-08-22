package com.gholem.moneylab.features.add.chooseTransactionCategory.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class InsertCategoryModelUseCase @Inject constructor(
    private val categoryStorageRepository: CategoryStorageRepository
) : UseCase<TransactionCategory, Unit> {

    override suspend fun run(input: TransactionCategory) {
        categoryStorageRepository.insert(input)
    }
}