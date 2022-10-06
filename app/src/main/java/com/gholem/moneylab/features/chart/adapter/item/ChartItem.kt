package com.gholem.moneylab.features.chart.adapter.item

import com.gholem.moneylab.domain.model.TransactionModel

sealed class ChartItem {
    data class Category(
        val transactionModel: TransactionModel
    ) : ChartItem()

    data class Pie(
        val transactionModelList: List<TransactionModel>
    ) : ChartItem()

    data class Bar(
        val transactionModelList: List<TransactionModel>
    ) : ChartItem()

    data class Transaction(
        val transactionModel: TransactionModel
    ) : ChartItem()
}
