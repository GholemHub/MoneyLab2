package com.gholem.moneylab.repository.network.dto.transaction

data class ExchangeResponse(
    val `data`: List<Data>,
    val timestamp: Long
)