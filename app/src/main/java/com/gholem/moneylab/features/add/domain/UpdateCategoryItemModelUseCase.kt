package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import javax.inject.Inject

class UpdateCategoryItemModelUseCase @Inject constructor(
    private val categoryStorageRepository: CategoryStorageRepository
) : UseCase<TransactionCategoryModel, Unit> {
    override suspend fun run(input1: TransactionCategoryModel) {
        categoryStorageRepository.updateItem(input1)
    }
}