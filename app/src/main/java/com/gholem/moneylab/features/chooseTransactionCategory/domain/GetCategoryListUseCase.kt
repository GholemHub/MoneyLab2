package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val repository: CategoryStorageRepository
) : UseCase<Unit, List<TransactionCategoryModel>> {

    override suspend fun run(input: Unit): List<TransactionCategoryModel> =
        repository.getAll()
}