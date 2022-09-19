package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCaseTwoParameters
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import javax.inject.Inject

class UpdateTransactionModelUseCase @Inject constructor(
    private val transactionStorageRepository: TransactionStorageRepository
) : UseCaseTwoParameters<TransactionModel, Long, Unit> {
    override suspend fun BiConsumer(input1: TransactionModel, input2: Long) {
        transactionStorageRepository.updateItem(input1, input2)
    }
}