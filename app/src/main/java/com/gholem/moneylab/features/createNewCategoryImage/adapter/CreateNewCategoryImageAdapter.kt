package com.gholem.moneylab.features.createNewCategoryImage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.databinding.ItemNewCategoryImageBinding
import com.gholem.moneylab.domain.model.NewCategoryImageItem
import com.gholem.moneylab.domain.model.NewCategoryImageItem.Companion.getImages
import com.gholem.moneylab.features.createNewCategoryImage.adapter.viewholder.CreateNewCategoryViewHolder

class CreateNewCategoryImageAdapter(val imageClickListener: (position: Int) -> Unit) :
    RecyclerView.Adapter<CreateNewCategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateNewCategoryViewHolder {
        return createNewTransaction(parent)
    }

    private val adapterData = getImages().toMutableList()

    private fun createNewTransaction(parent: ViewGroup): CreateNewCategoryViewHolder {
        val binding = ItemNewCategoryImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewHolder =
            CreateNewCategoryViewHolder.NewCategoryImage(binding).also { viewHolder ->
                binding.newCategoryImage.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    var v = adapterData[position] as NewCategoryImageItem.Image
                    imageClickListener.invoke(v.image)
                }
            }

        return viewHolder
    }

    override fun getItemCount(): Int = adapterData.size

    override fun onBindViewHolder(holder: CreateNewCategoryViewHolder, position: Int) {
        when (holder) {
            is CreateNewCategoryViewHolder.NewCategoryImage -> holder.bind(adapterData[position] as NewCategoryImageItem.Image)
        }
    }
}