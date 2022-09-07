package com.gholem.moneylab.features.planning.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ItemPlanningBinding
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.planning.adapter.item.PersonsItem
import com.gholem.moneylab.features.planning.adapter.viewholder.PlanningViewHolder
import timber.log.Timber.i
import java.text.SimpleDateFormat

class PlanningAdapter : RecyclerView.Adapter<PlanningViewHolder>() {

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

    private fun creativeViewHolder(
        parent: ViewGroup
    ): PlanningViewHolder.PlanningDataViewHolder {
        val binding = ItemPlanningBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PlanningViewHolder.PlanningDataViewHolder(binding)
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

/*
    fun setListData2(listData: PersonsItem) {

        var list = mutableListOf<TransactionModel>()
        listData.data.forEach {
            var v = it.rank.toInt() % 6
            list.add( TransactionModel(
                category = TransactionCategoryModel("123",R.drawable.ic_category_other),
                amount = it.tradingPairs.toInt(),
                date = it.updated
            ))
        }
        this.adapterData = list
        notifyDataSetChanged()
    }
*/
    fun setListData(listData: List<TransactionModel>) {
        this.adapterData = listData
        notifyDataSetChanged()
    }
}