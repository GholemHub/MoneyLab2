package com.gholem.moneylab.repository.network.real

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.planning.adapter.item.PersonsItem
import com.gholem.moneylab.repository.network.TransactionApiRepository
import com.gholem.moneylab.repository.network.api.TransactionApi
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class TransactionApiNetworkRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi
) : TransactionApiRepository {

    override suspend fun getTransaction(): Response<PersonsItem> {
        try {
            val response = transactionApi.getData()
            return response
        } catch (e: Exception) {
            Timber.e(e.stackTraceToString())
            throw e
        }
    }

    override suspend fun saveTransaction(transaction: TransactionModel) {

    }
}

