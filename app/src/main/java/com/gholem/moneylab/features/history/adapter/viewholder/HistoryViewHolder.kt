package com.gholem.moneylab.features.history.adapter.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemHistoryDateBinding
import com.gholem.moneylab.databinding.ItemHistoryTransactionBinding
import com.gholem.moneylab.features.history.adapter.item.HistoryTransactionItem
import java.text.SimpleDateFormat
import java.util.*

sealed class HistoryViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class HistoryDateViewHolder(
        private val binding: ItemHistoryDateBinding
    ) : HistoryViewHolder(binding) {
        @SuppressLint("SimpleDateFormat")
        fun bind(historyDate: HistoryTransactionItem.HistoryDate) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = historyDate.date
            val dateFormat = SimpleDateFormat("dd.MM.yy")
            binding.historyDateTv.text = dateFormat.format(calendar.time)
        }
    }

    class HistoryTransactionViewHolder(
        private val binding: ItemHistoryTransactionBinding
    ) : HistoryViewHolder(binding) {
        fun bind(transaction: HistoryTransactionItem.HistoryTransaction) {
            binding.amount.text = transaction.amount
            binding.itemHistoryTransactionHeader.text = transaction.category.categoryName
            binding.categoryIcon.setImageResource(transaction.category.image)
        }
    }
}