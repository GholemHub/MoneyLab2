package com.gholem.moneylab.features.chart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ItemChartDateBinding
import com.gholem.moneylab.databinding.ItemChartEmptyBinding
import com.gholem.moneylab.databinding.ItemChartTransactionBinding
import com.gholem.moneylab.domain.model.AddTransactionItem
import com.gholem.moneylab.domain.model.ChartTransactionItem
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.features.chart.adapter.viewholder.ChartViewHolder

class ChartAdapter : RecyclerView.Adapter<ChartViewHolder>() {

    private var adapterData = ChartTransactionItem.getRoomData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        return when (viewType) {
            R.layout.item_chart_date -> createChartDateHolder(parent)
            R.layout.item_chart_transaction -> createChartTransactionHolder(parent)
            R.layout.item_chart_empty -> createChartEmptyHolder(parent)

            else -> throw IllegalArgumentException("Invalid view type $viewType, size ${adapterData.size}")
        }
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        when (holder) {
            is ChartViewHolder.ChartDateViewHolder -> holder.bind(adapterData[position] as ChartTransactionItem.ChartDate)
            is ChartViewHolder.ChartTransactionViewHolder -> holder.bind(adapterData[position] as ChartTransactionItem.ChartTransaction)
        }
    }

    override fun getItemCount(): Int = adapterData.size

    private fun createChartDateHolder(
        parent: ViewGroup
    ): ChartViewHolder.ChartDateViewHolder {
        val binding = ItemChartDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChartViewHolder.ChartDateViewHolder(binding)
    }

    private fun createChartEmptyHolder(
        parent: ViewGroup
    ): ChartViewHolder.ChartEmptyViewHolder {
        val binding = ItemChartEmptyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChartViewHolder.ChartEmptyViewHolder(binding)
    }

    private fun createChartTransactionHolder(
        parent: ViewGroup
    ): ChartViewHolder.ChartTransactionViewHolder {
        val binding = ItemChartTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChartViewHolder.ChartTransactionViewHolder(binding)
    }
}