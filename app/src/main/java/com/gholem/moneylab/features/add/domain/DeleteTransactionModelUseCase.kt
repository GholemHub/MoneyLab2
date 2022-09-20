package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import javax.inject.Inject

class DeleteTransactionModelUseCase @Inject constructor(
    private val transactionStorageRepository: TransactionStorageRepository
) : UseCase<Int, Unit> {

    override suspend fun run(input: Int) {
        transactionStorageRepository.deleteItem(input)
    }
}