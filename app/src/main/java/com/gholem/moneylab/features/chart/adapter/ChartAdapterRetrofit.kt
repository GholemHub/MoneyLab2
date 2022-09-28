package com.gholem.moneylab.features.chart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.databinding.ItemChartRetrofitBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.chart.adapter.viewholder.ChartViewHolderRetrofit

class ChartAdapterRetrofit(val positionClickListener: (item: TransactionModel) -> Unit) :
    RecyclerView.Adapter<ChartViewHolderRetrofit>() {

    private var adapterData: List<TransactionModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolderRetrofit =
        creativeViewHolder(parent)

    override fun onBindViewHolder(holder: ChartViewHolderRetrofit, position: Int) {
        holder as ChartViewHolderRetrofit.ChartDataViewHolderRetrofit
        adapterData.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int = adapterData.size

    fun setListData(listData: List<TransactionModel>) {
        this.adapterData = listData
        notifyDataSetChanged()
    }

    private fun creativeViewHolder(
        parent: ViewGroup
    ): ChartViewHolderRetrofit.ChartDataViewHolderRetrofit {
        val binding = ItemChartRetrofitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val viewHolder =
            ChartViewHolderRetrofit.ChartDataViewHolderRetrofit(binding).also { viewHolder ->
                binding.root.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    positionClickListener.invoke(adapterData[position])
                }
            }
        return viewHolder
    }
}