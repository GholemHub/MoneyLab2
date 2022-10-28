package com.gholem.moneylab.repository.network.real

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.network.dto.transaction.ExchangeResponse
import com.gholem.moneylab.repository.network.TransactionApiRepository
import com.gholem.moneylab.repository.network.api.TransactionApi
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class TransactionApiNetworkRepository @Inject constructor(
    private val transactionApi: TransactionApi
) : TransactionApiRepository {

    override suspend fun getTransaction(): Response<ExchangeResponse> {
        try {
            return transactionApi.getExchanges()
        } catch (e: Exception) {
            Timber.e(e.stackTraceToString())
            throw e
        }
    }

    override suspend fun saveTransaction(transaction: TransactionModel) {

    }
}