package com.gholem.moneylab.repository.network

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.planning.adapter.item.PersonsItem
import retrofit2.Response

interface TransactionApiRepository {

    suspend fun getTransaction(): Response<PersonsItem>?

    suspend fun saveTransaction(transaction: TransactionModel)
}