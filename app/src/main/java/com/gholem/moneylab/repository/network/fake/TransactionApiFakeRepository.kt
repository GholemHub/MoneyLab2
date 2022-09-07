package com.gholem.moneylab.repository.network.fake

import com.gholem.moneylab.R
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.planning.adapter.item.PersonsItem
import com.gholem.moneylab.repository.network.TransactionApiRepository
import retrofit2.Response

class TransactionApiFakeRepository : TransactionApiRepository {

    override suspend fun getTransaction(): Response<PersonsItem>? = null
    override suspend fun saveTransaction(transaction: TransactionModel) = Unit
}