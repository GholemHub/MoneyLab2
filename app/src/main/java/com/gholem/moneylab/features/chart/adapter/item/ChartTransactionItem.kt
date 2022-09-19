package com.gholem.moneylab.features.chart.adapter.item

import com.gholem.moneylab.domain.model.TransactionCategoryModel

sealed class ChartTransactionItem {
    data class ChartDate(
        var date: Long = System.currentTimeMillis()
    ) : ChartTransactionItem()

    data class ChartTransaction(
        var category: TransactionCategoryModel,
        var amount: String = ""

    ) : ChartTransactionItem()
}