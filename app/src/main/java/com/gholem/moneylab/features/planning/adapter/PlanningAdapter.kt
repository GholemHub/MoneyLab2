package com.gholem.moneylab.features.planning.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.databinding.ItemPlanningBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.planning.adapter.viewholder.PlanningViewHolder

class PlanningAdapter(val positionClickListener: (item: TransactionModel) -> Unit) :
    RecyclerView.Adapter<PlanningViewHolder>() {

    private var adapterData: List<TransactionModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder =
        creativeViewHolder(parent)

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        holder as PlanningViewHolder.PlanningDataViewHolder
        adapterData.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int = adapterData.size

    fun setListData(listData: List<TransactionModel>) {
        this.adapterData = listData
        notifyDataSetChanged()
    }

    private fun creativeViewHolder(
        parent: ViewGroup
    ): PlanningViewHolder.PlanningDataViewHolder {
        val binding = ItemPlanningBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val viewHolder = PlanningViewHolder.PlanningDataViewHolder(binding).also { viewHolder ->
            binding.root.setOnClickListener {
                val position = viewHolder.adapterPosition
                positionClickListener.invoke(adapterData[position])
            }
        }
        return viewHolder
    }
}