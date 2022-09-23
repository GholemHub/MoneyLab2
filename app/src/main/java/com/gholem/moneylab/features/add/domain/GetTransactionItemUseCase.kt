package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import javax.inject.Inject

class GetTransactionItemUseCase @Inject constructor(
    private val repository: TransactionStorageRepository
) : UseCase<Long, TransactionModel> {

    override suspend fun run(input: Long): TransactionModel =
        repository.getItemById(input)
}