package com.gholem.moneylab.features.chart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.databinding.ItemChartRetrofitBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.util.timestampToString

sealed class ChartViewHolderRetrofit(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class ChartDataViewHolderRetrofit(
        private val binding: ItemChartRetrofitBinding
    ) : ChartViewHolderRetrofit(binding) {

        fun bind(data: TransactionModel) {
            binding.categoryName.text = data.category.categoryName
            binding.amount.text = data.amount.toString()
            binding.date.text = data.date.timestampToString()
        }
    }
}