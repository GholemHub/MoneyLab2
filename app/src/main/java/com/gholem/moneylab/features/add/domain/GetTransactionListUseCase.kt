package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import javax.inject.Inject

class GetTransactionListUseCase @Inject constructor(
    private val repository: TransactionStorageRepository
) : UseCase<Unit, List<TransactionEntity>> {

    override suspend fun run(input: Unit): List<TransactionEntity> =
        repository.getAll()
}