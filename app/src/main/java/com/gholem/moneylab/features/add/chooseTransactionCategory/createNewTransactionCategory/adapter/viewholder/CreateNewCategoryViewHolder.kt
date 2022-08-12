package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewTransactionCategory.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemCategoryBinding
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewTransactionCategory.viewmodel.CreateNewTransactionViewModel

sealed class CreateNewCategoryViewHolder(binding: ViewBinding) :
RecyclerView.ViewHolder(binding.root){
    class NewCategoryImage(
        private val binding: ItemCategoryBinding
    ): CreateNewCategoryViewHolder(binding){

    }
}