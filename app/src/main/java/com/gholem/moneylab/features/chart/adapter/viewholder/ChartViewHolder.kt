package com.gholem.moneylab.features.chart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemChartDateBinding
import com.gholem.moneylab.databinding.ItemChartTransactionBinding
import com.gholem.moneylab.domain.model.ChartTransactionItem

sealed class ChartViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class ChartDateViewHolder(
        private val binding: ItemChartDateBinding
    ) : ChartViewHolder(binding) {
        fun bind(category: ChartTransactionItem.ChartDate) {
            binding.chartDateTv.text = category.date.toString()
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