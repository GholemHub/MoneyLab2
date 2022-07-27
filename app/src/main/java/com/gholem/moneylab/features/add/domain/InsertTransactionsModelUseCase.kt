package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import javax.inject.Inject

class InsertTransactionsModelUseCase @Inject constructor(
    private val transactionStorageRepository: TransactionStorageRepository
) : UseCase<List<Transaction>, Unit> {

    override suspend fun run(input: List<Transaction>) {
        transactionStorageRepository.insert(input)
    }
}