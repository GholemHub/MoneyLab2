package com.gholem.moneylab.features.chart.adapter.item

import com.gholem.moneylab.domain.model.TransactionModel

sealed class ChartItem {
    data class Category(
        val categpry: TransactionModel
    ) : ChartItem()

    data class Pie(
        val transactionModel: List<TransactionModel>
    ) : ChartItem()

    data class Bar(
        val transactionModel: List<TransactionModel>
    ) : ChartItem()

    data class Retrofit(
        val transactionModel: TransactionModel
    ) : ChartItem()
}
