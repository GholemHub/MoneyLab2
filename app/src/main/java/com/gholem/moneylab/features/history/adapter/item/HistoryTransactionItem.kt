package com.gholem.moneylab.features.history.adapter.item

import com.gholem.moneylab.domain.model.TransactionCategoryModel

sealed class HistoryTransactionItem {
    data class HistoryDate(
        val date: Long = System.currentTimeMillis()
    ) : HistoryTransactionItem()

    data class HistoryTransaction(
        val category: TransactionCategoryModel,
        val amount: String = "",
        val id: Long
    ) : HistoryTransactionItem()
}