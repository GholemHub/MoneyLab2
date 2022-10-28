package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class InsertCategoriesModelUseCase @Inject constructor(
    private val categoryStorageRepository: CategoryStorageRepository
) : UseCase<List<CategoryItem>, Unit> {

    override suspend fun run(input: List<CategoryItem>): Unit =
        categoryStorageRepository.insertList(input)
}