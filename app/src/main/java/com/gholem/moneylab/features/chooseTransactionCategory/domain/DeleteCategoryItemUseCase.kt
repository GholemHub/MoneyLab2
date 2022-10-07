package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class DeleteCategoryItemUseCase @Inject constructor(
    private val repository: CategoryStorageRepository
) : UseCase<Int, Unit> {

    override suspend fun run(input: Int): Unit =
        repository.deleteItem(input)
}