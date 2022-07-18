package com.gholem.moneylab.features.add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ItemCategoryBinding
import com.gholem.moneylab.databinding.ItemNewTransactionBinding
import com.gholem.moneylab.databinding.ItemTransactionBinding
import com.gholem.moneylab.domain.model.AddNextTransaction
import com.gholem.moneylab.features.add.adapter.viewholder.AddTransactionViewHolder

class AddTransactionsAdapter : RecyclerView.Adapter<AddTransactionViewHolder>() {

    private var adapterData = mutableListOf<AddNextTransaction>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTransactionViewHolder {
        return when (viewType) {
            R.layout.item_category -> AddTransactionViewHolder.CategoryViewHolder(
                ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_transaction -> AddTransactionViewHolder.TransactionViewHolder(
                ItemTransactionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_new_transaction -> AddTransactionViewHolder.NewTransactionViewHolder(
                ItemNewTransactionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: AddTransactionViewHolder, position: Int) {
        when (holder) {
            is AddTransactionViewHolder.CategoryViewHolder -> holder.bind(adapterData[position] as AddNextTransaction.Category)
            is AddTransactionViewHolder.TransactionViewHolder -> holder.bind(adapterData[position] as AddNextTransaction.Transaction)
            is AddTransactionViewHolder.NewTransactionViewHolder -> holder.bind(adapterData[position] as AddNextTransaction.NewTransaction)
        }
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is AddNextTransaction.Category -> R.layout.item_category
            is AddNextTransaction.Transaction -> R.layout.item_transaction
            is AddNextTransaction.NewTransaction -> R.layout.item_new_transaction

            else -> throw IllegalArgumentException("Invalid view type getItemView")
        }
    }

    fun setData(data: List<AddNextTransaction>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }
}