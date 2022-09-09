package com.gholem.moneylab.repository.network.api

import com.gholem.moneylab.repository.network.dto.transaction.ExchangeResponse
import retrofit2.Response
import retrofit2.http.GET

interface TransactionApi {

    @GET("/v2/exchanges")
    suspend fun getExchanges(): Response<ExchangeResponse>
}