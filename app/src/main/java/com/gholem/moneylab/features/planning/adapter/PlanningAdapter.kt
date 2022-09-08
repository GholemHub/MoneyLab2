package com.gholem.moneylab.features.planning.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ItemPlanningBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.planning.adapter.viewholder.PlanningViewHolder
import timber.log.Timber.i

class PlanningAdapter(val positionClickListener: (position: Int) -> Unit) :
    RecyclerView.Adapter<PlanningViewHolder>() {

    private var adapterData: List<TransactionModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        return when (viewType) {
            R.layout.item_planning -> creativeViewHolder(parent)
            else -> throw IllegalArgumentException(
                "Invalid view type $viewType, " +
                        "size ${adapterData.size}"
            )
        }
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        i("adapterData $adapterData")
        when (holder) {
            is PlanningViewHolder.PlanningDataViewHolder -> adapterData.let {
                holder.bind(it[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            else -> R.layout.item_planning
        }
    }

    override fun getItemCount(): Int = adapterData.size

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
                positionClickListener.invoke(position)
            }
        }
        return viewHolder
    }

    fun setListData(listData: List<TransactionModel>) {
        this.adapterData = listData
        notifyDataSetChanged()
    }
}