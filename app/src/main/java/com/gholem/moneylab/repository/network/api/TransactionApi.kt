package com.gholem.moneylab.repository.network.api

import com.gholem.moneylab.features.planning.adapter.item.CoinCapItem
import retrofit2.Response
import retrofit2.http.GET

interface TransactionApi {

    @GET("/v2/exchanges")
    suspend fun getDataFromCoinCap(): Response<CoinCapItem>
}