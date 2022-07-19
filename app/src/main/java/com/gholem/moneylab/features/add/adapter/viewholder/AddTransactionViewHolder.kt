package com.gholem.moneylab.features.add.adapter.viewholder

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemCategoryBinding
import com.gholem.moneylab.databinding.ItemNewTransactionBinding
import com.gholem.moneylab.databinding.ItemTransactionBinding
import com.gholem.moneylab.domain.model.AddNextTransaction
import com.gholem.moneylab.features.add.adapter.AddTransactionsAdapter
import java.util.*

sealed class AddTransactionViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {


    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        AddTransactionViewHolder(binding) {
        fun bind(category: AddNextTransaction.Category) {
            binding.nameOfCategory.text = category.name
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    class TransactionViewHolder(private val binding: ItemTransactionBinding,
                                listener: AddTransactionsAdapter.OnItemClickDataSetListener) :
        AddTransactionViewHolder(binding) {
        init {
            binding.setDataBtn.setOnClickListener {
                listener.onItemClick(adapterPosition).apply {
                    val sdf = SimpleDateFormat("dd.MM.yyyy")
                    val currentDate = sdf.format(Date())
                    binding.setDataBtn.text = currentDate
                }
            }
        }
            fun bind(transaction: AddNextTransaction.Transaction) {
                binding.setDataBtn.text = transaction.data
            }
        }

        class NewTransactionViewHolder(
            private val binding: ItemNewTransactionBinding,
            listener: AddTransactionsAdapter.OnItemClickAddListener
        ) :
            AddTransactionViewHolder(binding) {

            init {
                binding.createNewTransactionBtn.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
            }
            fun bind(newTransaction: AddNextTransaction.NewTransaction) {
                binding.createNewTransactionBtn.text = newTransaction.add
            }
        }
}