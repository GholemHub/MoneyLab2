package com.gholem.moneylab.features.chart.adapter.item

sealed class ChartItem {
    data class Category(
        val categpry: ChartCategoryModel
    ) : ChartItem()

    data class Pie(
        val transactionModel: List<ChartCategoryModel>
    ) : ChartItem()

    data class Bar(
        val transactionModel: List<ChartCategoryModel>
    ) : ChartItem()
}
