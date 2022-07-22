package com.gholem.moneylab.features.add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ItemCategoryBinding
import com.gholem.moneylab.databinding.ItemNewTransactionBinding
import com.gholem.moneylab.databinding.ItemTransactionBinding
import com.gholem.moneylab.domain.model.AddTransactionItem
import com.gholem.moneylab.features.add.adapter.viewholder.AddTransactionViewHolder

class AddTransactionsAdapter : RecyclerView.Adapter<AddTransactionViewHolder>() {

    private lateinit var addListener: OnItemClickAddListener
    private lateinit var dataSetListener: OnItemClickDataSetListener

    //Where should be interfaces in Adapter in Viewmodel or in ViewHolder
    interface OnItemClickAddListener {
        fun onItemClick(position: Int)
    }

    interface OnItemClickDataSetListener {
        fun onItemClick(position: Int)
    }

    private var adapterData = mutableListOf<AddTransactionItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTransactionViewHolder {
        ClickListenerOfAddListener()
        ClickListenerOfDataSetListener()

        return SetTypeAddTransactionViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: AddTransactionViewHolder, position: Int) {
        when (holder) {
            is AddTransactionViewHolder.CategoryViewHolder -> holder.bind(adapterData[position] as AddTransactionItem.Category)
            is AddTransactionViewHolder.TransactionViewHolder -> holder.bind(adapterData[position] as AddTransactionItem.Transaction)
            is AddTransactionViewHolder.NewTransactionViewHolder -> holder.bind(adapterData[position] as AddTransactionItem.NewTransaction)
        }
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is AddTransactionItem.Category -> R.layout.item_category
            is AddTransactionItem.Transaction -> R.layout.item_transaction
            is AddTransactionItem.NewTransaction -> R.layout.item_new_transaction

            else -> throw IllegalArgumentException("Invalid view type getItemView")
        }
    }

    private fun SetTypeAddTransactionViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddTransactionViewHolder {
        return when (viewType) {
            R.layout.item_category -> AddTransactionViewHolder.CategoryViewHolder(
                ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_transaction -> AddTransactionViewHolder.TransactionViewHolder(
                ItemTransactionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                dataSetListener
            )
            R.layout.item_new_transaction -> AddTransactionViewHolder.NewTransactionViewHolder(
                ItemNewTransactionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                addListener
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    fun setOnItemClickAddListener(listener: OnItemClickAddListener) {
        addListener = listener
    }

    fun setOnItemClickDataSetListener(listener: OnItemClickDataSetListener) {
        dataSetListener = listener
    }

    private fun ClickListenerOfDataSetListener() {
        this.setOnItemClickDataSetListener(object :
            AddTransactionsAdapter.OnItemClickDataSetListener {
            override fun onItemClick(position: Int) {
            }
        })
    }

    private fun ClickListenerOfAddListener() {
        this.setOnItemClickAddListener(object : AddTransactionsAdapter.OnItemClickAddListener {
            override fun onItemClick(position: Int) {
                val startPosition = adapterData.size - 1
                adapterData.add(startPosition, AddTransactionItem.Transaction(12, "dada"))
                notifyItemRangeChanged(startPosition, 2)
            }
        })
    }

    fun setData(data: List<AddTransactionItem>) {
        adapterData.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    fun swap(new: AddTransactionItem) {
        adapterData.add(new)
        adapterData[adapterData.size - 1] = adapterData[adapterData.size - 2]
        adapterData[adapterData.size - 2] = new
    }
}