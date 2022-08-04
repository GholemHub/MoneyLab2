package com.gholem.moneylab.domain.model

sealed class ChartTransactionItem {
    data class ChartDate(
        var date: Long = System.currentTimeMillis()
    ) : ChartTransactionItem()

    data class ChartTransaction(
        var category: TransactionCategory,
        var amount: String = ""

    ) : ChartTransactionItem()

    object ChartEmpty : ChartTransactionItem()

    companion object {
        var listOfChartTransaction : List<ChartTransactionItem> = emptyList()

        fun getRoomData(): List<ChartTransactionItem> {
            return listOfChartTransaction
        }

        fun getDefaultItems(): List<ChartTransactionItem> =
            listOf(
                ChartEmpty
                //ChartDate(),
                //ChartTransactionItem.ChartTransaction(TransactionCategory.getDefault()),
            )
    }
}