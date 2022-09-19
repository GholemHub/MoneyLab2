package com.gholem.moneylab.features.createNewCategoryImage.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemNewCategoryImageBinding
import com.gholem.moneylab.features.createNewCategoryImage.adapter.item.NewCategoryImageItem

sealed class CreateNewCategoryViewHolder(binding: ViewBinding) :
RecyclerView.ViewHolder(binding.root){
    class NewCategoryImage(

        private val binding: ItemNewCategoryImageBinding
    ): CreateNewCategoryViewHolder(binding){
        fun bind(image: NewCategoryImageItem.Image) {
            binding.newCategoryImage.setImageResource(image.image)
        }
    }
}