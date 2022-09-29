package com.gholem.moneylab.features.chart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemChartBarBinding
import com.gholem.moneylab.databinding.ItemChartCategoryBinding
import com.gholem.moneylab.databinding.ItemChartPieBinding
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

sealed class ChartViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class ChartCategoryViewHolder(
        private val binding: ItemChartCategoryBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Category) {
            binding.itemChartAmount.text = data.categpry.amount.toString()
            binding.itemChartCategoryIcon.setImageResource(data.categpry.transactionCategoryModel.image)
            binding.itemChartTransactionHeader.text = data.categpry.transactionCategoryModel.categoryName
        }
    }

    class ChartPieViewHolder(
        private val binding: ItemChartPieBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Pie) {
            var pieEntries = mutableListOf<PieEntry>()

            data.transactionModel.forEachIndexed{index, it ->
                var pieEntry = PieEntry(it.amount.toFloat(),index.toFloat())
                pieEntries.add(pieEntry)
            }

            var pieDataSet = PieDataSet(pieEntries, "Categoties")
            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 1000)
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
            var barEntries = mutableListOf<BarEntry>()

            data.transactionModel.forEachIndexed{index, it ->
                var barEntry = BarEntry(index.toFloat(),it.amount.toFloat())
                barEntries.add(barEntry)
            }

            var barDataSet = BarDataSet(barEntries, "Categoties")
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 1000)
            barDataSet.valueTextSize = 13f
            binding.barChart.data = BarData(barDataSet)
            binding.barChart.description.isEnabled = false
        }
    }
}