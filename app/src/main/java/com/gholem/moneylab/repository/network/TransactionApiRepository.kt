package com.gholem.moneylab.repository.network

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.network.dto.transaction.ExchangeResponse
import retrofit2.Response

interface TransactionApiRepository {

    suspend fun getTransaction(): Response<ExchangeResponse>?

    suspend fun saveTransaction(transaction: TransactionModel)
}