package com.gholem.moneylab.features.planning.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemPlanningBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.util.timestampToString

sealed class PlanningViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class PlanningDataViewHolder(
        private val binding: ItemPlanningBinding
    ) : PlanningViewHolder(binding) {

        fun bind(data: TransactionModel) {
            binding.categoryName.text = data.category.categoryName
            binding.amount.text = data.amount.toString()
            binding.date.text = data.date.timestampToString()
        }
    }
}