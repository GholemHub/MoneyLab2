package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.CategoryItem.ExpenseCategoryModel
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class InsertCategoryModelUseCase @Inject constructor(
    private val categoryStorageRepository: CategoryStorageRepository
) : UseCase<CategoryItem, Long> {

    override suspend fun run(input: CategoryItem): Long =
        categoryStorageRepository.insert(input)
}