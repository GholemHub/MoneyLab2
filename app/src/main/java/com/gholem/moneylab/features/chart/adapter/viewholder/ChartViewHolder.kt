package com.gholem.moneylab.features.chart.adapter.viewholder

import android.graphics.Color
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.*
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.gholem.moneylab.util.timestampToString
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter


sealed class ChartViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val resources = binding.root.resources

    class ChartEmptyViewHolder(
        private val binding: ItemChartEmptyBinding
    ) : ChartViewHolder(binding)

    class ChartiveCategoryViewHolder(
        private val binding: ItemChartCategoryBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Category, position: Int) {
            binding.itemChartCategoryConstraintLayout.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    list[position - 2]
                )
            )

            binding.itemChartAmount.text = data.transactionModel.amount.toString()
            when (data.transactionModel.category) {
                is CategoryItem.ExpenseCategoryModel -> {
                    binding.itemChartCategoryIcon.setImageResource(data.transactionModel.category.image)
                    binding.itemChartTransactionHeader.text =
                        data.transactionModel.category.categoryName
                }
                is CategoryItem.IncomeCategoryModel -> {
                    binding.itemChartCategoryIcon.setImageResource(data.transactionModel.category.image)
                    binding.itemChartTransactionHeader.text =
                        data.transactionModel.category.categoryName
                }
            }
        }
    }

    class ChartPieViewHolder(
        private val binding: ItemChartPieBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Pie) {

            val pieEntries = mutableListOf<PieEntry>()
            var colorlist = mutableListOf<Int>()
            colorlist.addAll(list)

            data.transactionModelList.forEachIndexed { index, it ->
                if (it.category is CategoryItem.ExpenseCategoryModel) {
                    val pieEntry = PieEntry(it.amount.toFloat(), it.category.categoryName)
                    pieEntries.add(pieEntry)
                } else {
                    colorlist.remove(list[index])
                }
            }

            val pieDataSet = PieDataSet(pieEntries, "Categoties")
            pieDataSet.setColors(colorlist
                .map { resources.getColor(it, resources.newTheme()) })

            pieDataSet.valueTextSize = 10f
            pieDataSet.sliceSpace = 3f

            pieDataSet.valueLinePart1OffsetPercentage = 20f
            pieDataSet.valueLinePart1Length = 0.43f
            pieDataSet.valueLinePart2Length = .2f
            pieDataSet.valueTextColor = Color.BLACK
            pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            binding.pieChart.data = PieData(pieDataSet)
            binding.pieChart.data.setValueTextColor(Color.BLACK);
            binding.pieChart.data.setValueFormatter(formatter)
            binding.pieChart.setEntryLabelColor(Color.BLACK)
            binding.pieChart.setDrawEntryLabels(true)

            var totalExpense = 0
            data.transactionModelList.forEach {
                when(it.category){
                    is CategoryItem.ExpenseCategoryModel -> {
                        totalExpense += it.amount
                    }
                }
            }

            binding.pieChart.animateXY(1000, 3000)
            binding.pieChart.description.textSize =
                resources.getDimension(R.dimen.chart_total_expense_size_default)
            binding.pieChart.description.text = "$totalExpense    "
        }
    }

    class ChartBarViewHolder(
        private val binding: ItemChartBarBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Bar) {
            val barEntries = mutableListOf<BarEntry>()

            data.transactionModelList.forEachIndexed { index, it ->
                when (it.category) {
                    is CategoryItem.ExpenseCategoryModel -> {
                        val barEntry = BarEntry(index.toFloat(), -it.amount.toFloat())
                        barEntries.add(barEntry)
                    }
                    is CategoryItem.IncomeCategoryModel -> {
                        val barEntry = BarEntry(index.toFloat(), it.amount.toFloat())
                        barEntries.add(barEntry)
                    }
                }
            }

            val barDataSet = BarDataSet(barEntries, "Categoties")
            barDataSet.setColors(
                list
                    .map { resources.getColor(it, resources.newTheme()) })
            barDataSet.valueTextSize = 13f
            binding.barChart.xAxis.setDrawGridLines(false)
            binding.barChart.xAxis.setLabelCount(1, false)

            binding.barChart.setFitBars(true)
            binding.barChart.setDrawGridBackground(false)
            binding.barChart.isVerticalFadingEdgeEnabled = false
            binding.barChart.isVerticalScrollBarEnabled = false
            binding.barChart.setTouchEnabled(false)
            binding.barChart.animateXY(1000, 3000)
            binding.barChart.data = BarData(barDataSet)
            binding.barChart.data.setValueFormatter(formatter)
            binding.barChart.description.isEnabled = false
        }
    }

    class ChartDataRetrofitViewHolder(
        private val binding: ItemChartTransactionBinding
    ) : ChartViewHolder(binding) {

        fun bind(data: ChartItem.Transaction) {
            when (data.transactionModel.category) {
                is CategoryItem.ExpenseCategoryModel -> {
                    binding.categoryName.text = data.transactionModel.category.categoryName
                }
                is CategoryItem.IncomeCategoryModel -> {
                    binding.categoryName.text = data.transactionModel.category.categoryName
                }
            }

            binding.amount.text = data.transactionModel.amount.toString()
            binding.date.text = data.transactionModel.date.timestampToString()
        }
    }

    var formatter: ValueFormatter = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return "${value.toInt()}"
        }
    }

    companion object {
        val list = listOf<Int>(
            R.color.chart_red,
            R.color.chart_yellow,
            R.color.chart_green,
            R.color.chart_orange,
            R.color.chart_blue,
            R.color.chart_raspberry,
            R.color.chart_purple,
            R.color.chart_deepBlue,
            R.color.chart_gold,
            R.color.chart_cian,
            R.color.chart_leaf_green,
            R.color.chart_pink,
            R.color.chart_raspberry,
            R.color.chart_violet,
            R.color.chart_lemon,
            R.color.chart_green,
            R.color.chart_orange,
            R.color.chart_blue,
            R.color.chart_raspberry,
            R.color.chart_purple,
            R.color.chart_deepBlue,
            R.color.chart_gold,
            R.color.chart_cian,
            R.color.chart_leaf_green,
            R.color.chart_pink,
            R.color.chart_raspberry,
            R.color.chart_violet,
            R.color.chart_lemon,
        )
    }
}