package com.gholem.moneylab.features.history.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemHistoryDateBinding
import com.gholem.moneylab.databinding.ItemHistoryTransactionBinding
import com.gholem.moneylab.features.history.adapter.item.HistoryTransactionItem
import java.util.*

sealed class HistoryViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class HistoryDateViewHolder(
        private val binding: ItemHistoryDateBinding
    ) : HistoryViewHolder(binding) {
        fun bind(historyDate: HistoryTransactionItem.HistoryDate) {
            val rightNow: Calendar = Calendar.getInstance()
            rightNow.timeInMillis = historyDate.date
            val string = "${rightNow.get(Calendar.DAY_OF_MONTH)}.${rightNow.get(Calendar.MONTH)}.${
                rightNow.get(Calendar.YEAR)
            }"
            binding.historyDateTv.text = string
        }
    }

    class HistoryTransactionViewHolder(
        private val binding: ItemHistoryTransactionBinding
    ) : HistoryViewHolder(binding) {
        fun bind(transaction: HistoryTransactionItem.HistoryTransaction) {
            binding.amount.text = transaction.amount
            binding.categoryIcon.setImageResource(transaction.category.image)
        }
    }
}