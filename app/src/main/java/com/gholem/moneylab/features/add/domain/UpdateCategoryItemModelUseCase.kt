package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class UpdateCategoryItemModelUseCase @Inject constructor(
    private val categoryStorageRepository: CategoryStorageRepository
) : UseCase<CategoryItem, Unit> {
    override suspend fun run(input1: CategoryItem) {
        categoryStorageRepository.updateItem(input1)
    }
}