package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import javax.inject.Inject

class GetTransactionListUseCase @Inject constructor(
    private val repository: TransactionStorageRepository
) : UseCase<Unit, List<Transaction>> {

    override suspend fun run(input: Unit): List<Transaction> =
        repository.getAll()

}