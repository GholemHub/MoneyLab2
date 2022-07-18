package com.gholem.moneylab.features.add.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemCategoryBinding
import com.gholem.moneylab.databinding.ItemNewTransactionBinding
import com.gholem.moneylab.databinding.ItemTransactionBinding
import com.gholem.moneylab.domain.model.AddNextTransaction

sealed class AddTransactionViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        AddTransactionViewHolder(binding) {
        fun bind(category: AddNextTransaction.Category) {
            binding.nameOfCategory.text = category.name
        }
    }

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        AddTransactionViewHolder(binding) {
        fun bind(transaction: AddNextTransaction.Transaction) {
            binding.setDataBtn.text = transaction.data
        }
    }

    class NewTransactionViewHolder(private val binding: ItemNewTransactionBinding) :
        AddTransactionViewHolder(binding) {
        fun bind(newTransaction: AddNextTransaction.NewTransaction) {
            binding.createNewTransactionBtn.text = newTransaction.add
        }
    }

}