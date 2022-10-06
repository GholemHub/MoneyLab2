package com.gholem.moneylab.features.chart.adapter.viewholder

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.*
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.gholem.moneylab.util.timestampToString
import com.github.mikephil.charting.data.*

sealed class ChartViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val resources = binding.root.resources

    class ChartEmptyViewHolder(
        private val binding: ItemChartEmptyBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Category, position: Int) {
        }
    }

    class ChartiveCategoryViewHolder(
        private val binding: ItemChartCategoryBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Category, position: Int) {
            binding.itemChartCategoryConstraintLayout.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    ChartColors.values()[position - 2 % ChartColors.values().size].colorResId
                )
            )
            binding.itemChartAmount.text = data.transactionModel.amount.toString()
            binding.itemChartCategoryIcon.setImageResource(data.transactionModel.category.image)
            binding.itemChartTransactionHeader.text =
                data.transactionModel.category.categoryName
        }
    }

    class ChartPieViewHolder(
        private val binding: ItemChartPieBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Pie) {
            val pieEntries = mutableListOf<PieEntry>()

            data.transactionModelList.forEachIndexed { index, it ->
                val pieEntry = PieEntry(it.amount.toFloat(), index.toFloat())
                pieEntries.add(pieEntry)
            }

            val pieDataSet = PieDataSet(pieEntries, "Categoties")
            pieDataSet.setColors(
                ChartColors.values().map { resources.getColor(it.colorResId, resources.newTheme()) }
                    .toIntArray(), 1000
            )
            pieDataSet.valueTextSize = 13f

            binding.pieChart.data = PieData(pieDataSet)
            binding.pieChart.animateXY(1000, 3000)
            binding.pieChart.description.isEnabled = false
        }
    }

    class ChartBarViewHolder(
        private val binding: ItemChartBarBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Bar) {
            val barEntries = mutableListOf<BarEntry>()

            data.transactionModelList.forEachIndexed { index, it ->
                val barEntry = BarEntry(index.toFloat(), it.amount.toFloat())
                barEntries.add(barEntry)
            }
            val resources = binding.root.resources
            val barDataSet = BarDataSet(barEntries, "Categoties")
            barDataSet.setColors(
                ChartColors.values()
                    .map { resources.getColor(it.colorResId, resources.newTheme()) })
            barDataSet.valueTextSize = 13f
            binding.barChart.setTouchEnabled(false)
            binding.barChart.animateXY(1000, 3000)
            binding.barChart.data = BarData(barDataSet)
            binding.barChart.description.isEnabled = false
        }
    }

    class ChartDataRetrofitViewHolder(
        private val binding: ItemChartRetrofitBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Transaction) {
            binding.categoryName.text = data.transactionModel.category.categoryName
            binding.amount.text = data.transactionModel.amount.toString()
            binding.date.text = data.transactionModel.date.timestampToString()
        }
    }

    companion object {
        enum class ChartColors(@ColorRes val colorResId: Int) {
            RED(R.color.chart_red),
            YELLOW(R.color.chart_yellow),
            GREEN(R.color.chart_green),
            ORANGE(R.color.chart_orange),
            BLUE(R.color.chart_blue),
            RASPBERRY(R.color.chart_raspberry),
            PURPLE(R.color.chart_purple),
        }
    }
}