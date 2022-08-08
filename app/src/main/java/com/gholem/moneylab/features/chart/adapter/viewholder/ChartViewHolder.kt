package com.gholem.moneylab.features.chart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemChartDateBinding
import com.gholem.moneylab.databinding.ItemChartTransactionBinding
import com.gholem.moneylab.domain.model.ChartTransactionItem
import java.util.*

sealed class ChartViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class ChartDateViewHolder(
        private val binding: ItemChartDateBinding
    ) : ChartViewHolder(binding) {
        fun bind(category: ChartTransactionItem.ChartDate) {
            val rightNow: Calendar = Calendar.getInstance()
            rightNow.timeInMillis = category.date
            val string = "${rightNow.get(Calendar.DAY_OF_MONTH)}.${rightNow.get(Calendar.MONTH)}.${rightNow.get(Calendar.YEAR)}"
            binding.chartDateTv.text = string
        }
    }

    class ChartTransactionViewHolder(
        private val binding: ItemChartTransactionBinding
    ) : ChartViewHolder(binding) {
        fun bind(transaction: ChartTransactionItem.ChartTransaction) {
            binding.amount.text = transaction.amount
            binding.categoryIcon.setImageResource(transaction.category.image)
        }
    }
}