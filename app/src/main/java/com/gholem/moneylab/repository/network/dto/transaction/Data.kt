package com.gholem.moneylab.repository.network.dto.transaction

data class Data(
    val exchangeId: String,
    val exchangeUrl: String,
    val name: String,
    val percentTotalVolume: String,
    val rank: String,
    val socket: Boolean,
    val tradingPairs: String,
    val updated: Long,
    val volumeUsd: String
)