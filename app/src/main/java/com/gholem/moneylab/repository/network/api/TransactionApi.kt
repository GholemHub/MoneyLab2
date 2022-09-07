package com.gholem.moneylab.repository.network.api

import com.gholem.moneylab.features.planning.adapter.item.PersonsItem
import com.gholem.moneylab.repository.network.dto.TransactionRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApi {

    //Response
    //https://api.coincap.io/v2/exchanges
    @GET("/v2/exchanges")
    suspend fun getData(): Response<PersonsItem>

    @POST("")
    suspend fun saveTransactionMethod(@Body transactionRequest: TransactionRequest)
}