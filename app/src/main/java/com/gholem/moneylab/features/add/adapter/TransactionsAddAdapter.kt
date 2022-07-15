package com.gholem.moneylab.features.add.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.domain.model.AddFragmentDataModel

class TransactionsAddAdapter : RecyclerView.Adapter<TransactionsAddAdapter.ViewHolder>() {

    private val adapterData = mutableListOf<AddFragmentDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when (viewType) {
            TYPE_CATEGORY -> R.layout.item_category
            TYPE_TRANSACTION -> R.layout.item_transaction
            TYPE_NEWTRANSACTION -> R.layout.item_new_transaction
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapterData[position])
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is AddFragmentDataModel.Category -> TYPE_CATEGORY
            is AddFragmentDataModel.Transaction -> TYPE_TRANSACTION
            else -> TYPE_NEWTRANSACTION
        }
    }

    fun setData(data: List<AddFragmentDataModel>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private fun bindCategory(item: AddFragmentDataModel.Category) {
            itemView.findViewById<TextView>(R.id.name_of_category).text = item.name
        }

        private fun bindTransaction(item: AddFragmentDataModel.Transaction) {
            itemView.findViewById<EditText>(R.id.amount_category).hint = item.amount.toString()
            itemView.findViewById<Button>(R.id.set_data_btn).text = item.data
        }

        private fun bindNewTransaction(item: AddFragmentDataModel.NewTransaction) {
            itemView.findViewById<Button>(R.id.create_new_transaction_btn).text = item.add
        }

        fun bind(dataModel: AddFragmentDataModel) {
            when (dataModel) {
                is AddFragmentDataModel.Category -> bindCategory(dataModel)
                is AddFragmentDataModel.Transaction -> bindTransaction(dataModel)
                is AddFragmentDataModel.NewTransaction -> bindNewTransaction(dataModel)
            }
        }
    }

    companion object {
        private const val TYPE_CATEGORY = 0
        private const val TYPE_TRANSACTION = 1
        private const val TYPE_NEWTRANSACTION = 2
    }
}