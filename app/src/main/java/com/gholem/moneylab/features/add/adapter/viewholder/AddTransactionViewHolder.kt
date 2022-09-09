package com.gholem.moneylab.features.add.adapter.viewholder

import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemCategoryBinding
import com.gholem.moneylab.databinding.ItemNewTransactionBinding
import com.gholem.moneylab.databinding.ItemTransactionBinding
import com.gholem.moneylab.features.add.adapter.item.AddTransactionItem
import com.gholem.moneylab.util.timestampToString
import timber.log.Timber.i

sealed class AddTransactionViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : AddTransactionViewHolder(binding) {
        fun bind(category: AddTransactionItem.Category) {

            binding.categoryButton.setText(category.category.categoryName)
            binding.categoryButton
                .setCompoundDrawablesWithIntrinsicBounds(0, 0, category.category.image, 0)
        }
    }

    class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : AddTransactionViewHolder(binding) {

        fun bind(transaction: AddTransactionItem.Transaction) {
            binding.setDateBtn.text = transaction.date.timestampToString()
            binding.amount.setText(transaction.amount)
            binding.amount.doAfterTextChanged {
                transaction.amount = it?.toString() ?: ""
            }
        }
    }

    class NewTransactionViewHolder(
        private val binding: ItemNewTransactionBinding
    ) : AddTransactionViewHolder(binding) {

        fun bind(newTransaction: AddTransactionItem.NewTransaction) {
        }
    }
}