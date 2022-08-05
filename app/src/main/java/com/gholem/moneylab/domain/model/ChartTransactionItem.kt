package com.gholem.moneylab.domain.model

sealed class ChartTransactionItem {
    data class ChartDate(
        var date: Long = System.currentTimeMillis()
    ) : ChartTransactionItem()

    data class ChartTransaction(
        var category: TransactionCategory,
        var amount: String = ""

    ) : ChartTransactionItem()
}