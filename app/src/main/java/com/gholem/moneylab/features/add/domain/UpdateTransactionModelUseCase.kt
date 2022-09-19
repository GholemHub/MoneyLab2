package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.arch.usecase.UseCaseTwoParameters
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import javax.inject.Inject

class UpdateTransactionModelUseCase @Inject constructor(
    private val transactionStorageRepository: TransactionStorageRepository
) : UseCaseTwoParameters<Transaction, Long, Unit> {
    override suspend fun BiConsumer(input1: Transaction, input2: Long) {
        transactionStorageRepository.update(input1, input2)
    }
}