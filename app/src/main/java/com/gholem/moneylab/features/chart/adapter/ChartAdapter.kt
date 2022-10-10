package com.gholem.moneylab.features.chart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.*
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.gholem.moneylab.features.chart.adapter.viewholder.ChartViewHolder

class ChartAdapter(
    private val adapterData: MutableList<ChartItem>,
    val positionClickListener: (item: ChartItem) -> Unit
) : RecyclerView.Adapter<ChartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        return createViewHolders(parent, viewType)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {

        when (holder) {
            is ChartViewHolder.ChartiveCategoryViewHolder ->
                holder.bind(adapterData[position] as ChartItem.Category, position)
            is ChartViewHolder.ChartPieViewHolder -> holder.bind(adapterData[position] as ChartItem.Pie)
            is ChartViewHolder.ChartBarViewHolder -> holder.bind(adapterData[position] as ChartItem.Bar)
            is ChartViewHolder.ChartDataRetrofitViewHolder -> holder.bind(adapterData[position] as ChartItem.Transaction)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is ChartItem.Category -> R.layout.item_chart_category
            is ChartItem.Pie -> R.layout.item_chart_pie
            is ChartItem.Bar -> R.layout.item_chart_bar
            is ChartItem.Transaction -> R.layout.item_chart_transaction
            is ChartItem.Empty -> R.layout.item_chart_empty
        }
    }

    override fun getItemCount(): Int = adapterData.size

    private fun createViewHolders(
        parent: ViewGroup,
        viewType: Int
    ): ChartViewHolder {
        return when (viewType) {
            R.layout.item_chart_category -> createCategoryViewHolder(parent)
            R.layout.item_chart_pie -> createPieViewHolder(parent)
            R.layout.item_chart_bar -> createBarViewHolder(parent)
            R.layout.item_chart_transaction -> createTransactionViewHolder(parent)
            R.layout.item_chart_empty -> createEmptyViewHolder(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    private fun createTransactionViewHolder(
        parent: ViewGroup
    ): ChartViewHolder.ChartDataRetrofitViewHolder {
        val binding = ItemChartTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChartViewHolder.ChartDataRetrofitViewHolder(binding).also { viewHolder ->
            binding.root.setOnClickListener {
                val position = viewHolder.adapterPosition
                positionClickListener.invoke(adapterData[position])
            }
        }
    }

    private fun createEmptyViewHolder(
        parent: ViewGroup
    ): ChartViewHolder.ChartEmptyViewHolder {
        val binding = ItemChartEmptyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChartViewHolder.ChartEmptyViewHolder(binding)
    }

    private fun createCategoryViewHolder(
        parent: ViewGroup
    ): ChartViewHolder.ChartiveCategoryViewHolder {
        val binding = ItemChartCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChartViewHolder.ChartiveCategoryViewHolder(binding)
    }

    private fun createPieViewHolder(
        parent: ViewGroup
    ): ChartViewHolder.ChartPieViewHolder {
        val binding = ItemChartPieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChartViewHolder.ChartPieViewHolder(binding)
    }

    private fun createBarViewHolder(
        parent: ViewGroup
    ): ChartViewHolder.ChartBarViewHolder {
        val binding = ItemChartBarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChartViewHolder.ChartBarViewHolder(binding)
    }

    fun createAdapterData(list: List<TransactionModel>) {
        adapterData.clear()

        if (list.isNotEmpty()) {
            adapterData.add(ChartItem.Pie(list))
            adapterData.add(ChartItem.Bar(list))
            adapterData.addAll(list.map { ChartItem.Category(it) })
        } else {
            adapterData.add(ChartItem.Empty)
        }

        notifyDataSetChanged()
    }

}