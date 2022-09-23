package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.arch.usecase.UseCaseTwoParameters
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import javax.inject.Inject

class UpdateTransactionModelUseCase @Inject constructor(
    private val transactionStorageRepository: TransactionStorageRepository
) : UseCase<TransactionModel, Unit> {
    override suspend fun run(input1: TransactionModel) {
        transactionStorageRepository.updateItem(input1)
    }
}