package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val repository: CategoryStorageRepository
) : UseCase<Unit, List<CategoryItem>> {

    override suspend fun run(input: Unit): List<CategoryItem> =
        repository.getAll()
}