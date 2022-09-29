package com.gholem.moneylab.features.chart.adapter.viewholder

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemChartBarBinding
import com.gholem.moneylab.databinding.ItemChartCategoryBinding
import com.gholem.moneylab.databinding.ItemChartPieBinding
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.github.mikephil.charting.data.*

sealed class ChartViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class ChartCategoryViewHolder(
        private val binding: ItemChartCategoryBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Category, position: Int) {

            binding.itemChartCategoryConstraintLayout.setBackgroundColor(
                COLORS.get(
                    position-2 % COLORS.size
                )
            )
            binding.itemChartAmount.text = data.categpry.amount.toString()
            binding.itemChartCategoryIcon.setImageResource(data.categpry.transactionCategoryModel.image)
            binding.itemChartTransactionHeader.text =
                data.categpry.transactionCategoryModel.categoryName
        }
    }

    class ChartPieViewHolder(
        private val binding: ItemChartPieBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Pie) {
            var pieEntries = mutableListOf<PieEntry>()

            data.transactionModel.forEachIndexed { index, it ->
                var pieEntry = PieEntry(it.amount.toFloat(), index.toFloat())
                pieEntries.add(pieEntry)
            }

            var pieDataSet = PieDataSet(pieEntries, "Categoties")
            pieDataSet.setColors(COLORS.toIntArray(), 1000)
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

            data.transactionModel.forEachIndexed { index, it ->
                var barEntry = BarEntry(index.toFloat(), it.amount.toFloat())
                barEntries.add(barEntry)
            }

            var barDataSet = BarDataSet(barEntries, "Categoties")
            barDataSet.setColors(COLORS.toIntArray(), 1000)
            barDataSet.valueTextSize = 13f
            binding.barChart.animateXY(1000, 3000)
            binding.barChart.data = BarData(barDataSet)
            binding.barChart.description.isEnabled = false
        }
    }
    companion object{
        val COLORS = listOf(
            Color.rgb(255, 102, 0),
            rgb("#2ecc71"),
            Color.rgb(245, 199, 0),
            rgb("#e74c3c"),

            rgb("#3498db"),
            Color.rgb(179, 100, 53),
            Color.rgb(106, 150, 31),
            rgb("#f1c40f"),
            Color.rgb(193, 37, 82),
        )
        fun rgb(hex: String): Int {
            val color = hex.replace("#", "").toLong(16).toInt()
            val r = color shr 16 and 0xFF
            val g = color shr 8 and 0xFF
            val b = color shr 0 and 0xFF
            return Color.rgb(r, g, b)
        }
    }
}