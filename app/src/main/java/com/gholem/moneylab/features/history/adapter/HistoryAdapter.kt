package com.gholem.moneylab.features.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ItemHistoryDateBinding
import com.gholem.moneylab.databinding.ItemHistoryTransactionBinding
import com.gholem.moneylab.features.history.adapter.item.HistoryTransactionItem
import com.gholem.moneylab.features.history.adapter.viewholder.HistoryViewHolder

class HistoryAdapter(
    val editItemPosition: (position: Long) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {

    private var adapterData: List<HistoryTransactionItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return when (viewType) {
            R.layout.item_history_date -> createHistoryDateHolder(parent)
            R.layout.item_history_transaction -> createHistoryTransactionHolder(parent)

            else -> throw IllegalArgumentException("Invalid view type $viewType, size ${adapterData.size}")
        }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        when (holder) {
            is HistoryViewHolder.HistoryDateViewHolder -> holder.bind(adapterData[position] as HistoryTransactionItem.HistoryDate)
            is HistoryViewHolder.HistoryTransactionViewHolder -> holder.bind(adapterData[position] as HistoryTransactionItem.HistoryTransaction)
        }
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is HistoryTransactionItem.HistoryDate -> R.layout.item_history_date
            is HistoryTransactionItem.HistoryTransaction -> R.layout.item_history_transaction
        }
    }

    private fun createHistoryDateHolder(
        parent: ViewGroup
    ): HistoryViewHolder.HistoryDateViewHolder {
        val binding = ItemHistoryDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder.HistoryDateViewHolder(binding)
    }

    private fun createHistoryTransactionHolder(
        parent: ViewGroup
    ): HistoryViewHolder.HistoryTransactionViewHolder {
        val binding = ItemHistoryTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewHolder =
            HistoryViewHolder.HistoryTransactionViewHolder(binding)
        binding.root.setOnClickListener {
            val historyTransactionItem =
                adapterData[viewHolder.adapterPosition] is HistoryTransactionItem.HistoryTransaction
            historyTransactionItem.also {
                val transactionItem =
                    adapterData.get(viewHolder.adapterPosition) as HistoryTransactionItem.HistoryTransaction
                editItemPosition.invoke(transactionItem.id)
            }
        }
        return viewHolder
    }

    fun updateData(listOfTransaction: List<HistoryTransactionItem>) {
        adapterData = listOfTransaction
        notifyDataSetChanged()
    }
}