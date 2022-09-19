package com.gholem.moneylab.repository.network.fake

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.network.dto.transaction.ExchangeResponse
import com.gholem.moneylab.repository.network.TransactionApiRepository
import retrofit2.Response

class TransactionApiFakeRepository : TransactionApiRepository {

    override suspend fun getTransaction(): Response<ExchangeResponse>? = null
    override suspend fun saveTransaction(transaction: TransactionModel) = Unit
}