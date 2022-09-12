package com.gholem.moneylab.features.add.adapter.viewholder

import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.R
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

        fun bind(transaction: AddTransactionItem.Transaction, isInvalidData: Boolean) {
            binding.setDateBtn.text = transaction.date.timestampToString()
            i("DDD VM transaction.amount ${transaction}")
            binding.amountEditText.setText(transaction.amount)
            binding.amountEditText.doAfterTextChanged {
                transaction.amount = it?.toString() ?: ""
            }

            if (isInvalidData) {
                binding.amountInputLayout.error = "Value cannot be empty"
                binding.amountInputLayout.isErrorEnabled = true
            } else {
                binding.amountInputLayout.isErrorEnabled = false
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