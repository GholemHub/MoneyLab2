package com.gholem.moneylab.features.add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ItemCategoryBinding
import com.gholem.moneylab.databinding.ItemNewTransactionBinding
import com.gholem.moneylab.databinding.ItemTransactionBinding
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.adapter.item.AddTransactionItem
import com.gholem.moneylab.features.add.adapter.viewholder.AddTransactionViewHolder
import timber.log.Timber.i
import java.util.*

class AddTransactionsAdapter(
    private var adapterData: MutableList<AddTransactionItem>,
    private val categoryClickListener: () -> Unit,
    private val dateClickListener: (position: Int) -> Unit
) :
    RecyclerView.Adapter<AddTransactionViewHolder>() {

    private var listOfCategory: List<TransactionCategoryModel> = mutableListOf()
    private var listOfInvalidItemsIndexes = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTransactionViewHolder {
        return createViewHolders(parent, viewType)
    }

    override fun onBindViewHolder(holder: AddTransactionViewHolder, position: Int) {
        val isInvalidData = listOfInvalidItemsIndexes.contains(position)

        when (holder) {
            is AddTransactionViewHolder.CategoryViewHolder -> holder.bind(adapterData[position] as AddTransactionItem.Category)
            is AddTransactionViewHolder.TransactionViewHolder -> {
                holder.bind(
                    adapterData[position] as AddTransactionItem.Transaction,
                    isInvalidData
                )
            }
            is AddTransactionViewHolder.NewTransactionViewHolder -> Unit // Do nothing
        }
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is AddTransactionItem.Category -> R.layout.item_category
            is AddTransactionItem.Transaction -> R.layout.item_transaction
            is AddTransactionItem.NewTransaction -> R.layout.item_new_transaction
        }
    }

    fun updateData(listOfTrCategory: List<TransactionCategoryModel>) {
        listOfCategory = listOfTrCategory
        notifyDataSetChanged()
    }

    fun setDate(position: Int, day: Int, month: Int, year: Int) {
        val transaction = adapterData[position]
                as? AddTransactionItem.Transaction

        val rightNow: Calendar = Calendar.getInstance()
        rightNow.set(Calendar.MILLISECOND, 0)
        rightNow.set(Calendar.SECOND, 0)
        rightNow.set(Calendar.MINUTE, 0)
        rightNow.set(Calendar.HOUR, 0)
        rightNow.set(Calendar.DAY_OF_MONTH, day)
        rightNow.set(Calendar.MONTH, month)
        rightNow.set(Calendar.YEAR, year)

        transaction?.date = rightNow.timeInMillis

        transaction?.let {
            notifyItemChanged(position)
        }
    }

    fun setCategory(categoryId: Long) {
        val cat = adapterData.first {
            it is AddTransactionItem.Category
        } as AddTransactionItem.Category

        cat.category = listOfCategory.get(categoryId.toInt() - 1)

        notifyItemChanged(adapterData.indexOf(cat))
    }

    fun getTransactionListData(): List<TransactionModel> {
        val listTransactionModel = mutableListOf<TransactionModel>().apply {
            var currentCategory: TransactionCategoryModel? = null

            adapterData.forEach { item ->
                when (item) {
                    is AddTransactionItem.Category -> {
                        currentCategory = item.category
                    }
                    is AddTransactionItem.Transaction -> {
                        this.add(mapToTransaction(currentCategory, item))
                    }
                    else -> Unit // Do nothing
                }
            }
        }

        return listTransactionModel
    }

    fun setInvalidData(listOfIndexes: List<Int>) {
        listOfInvalidItemsIndexes = listOfIndexes
        notifyDataSetChanged()
    }

    private fun mapToTransaction(
        category: TransactionCategoryModel?,
        item: AddTransactionItem.Transaction
    ) =
        TransactionModel(
            category = category ?: listOfCategory[0],
            amount = item.amount.ifBlank { "0" }.toInt(),
            date = item.date,
            transactionId = item.id
        )

    private fun createViewHolders(
        parent: ViewGroup,
        viewType: Int
    ): AddTransactionViewHolder {
        return when (viewType) {
            R.layout.item_category -> createCategoryViewHolder(parent)
            R.layout.item_transaction -> createTransactionViewHolder(parent)
            R.layout.item_new_transaction -> createNewTransactionViewHolder(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    private fun createCategoryViewHolder(
        parent: ViewGroup
    ): AddTransactionViewHolder.CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        //here is using listener to push it from adapter to fragment
        binding.categoryButton.setOnClickListener {
            categoryClickListener.invoke()
        }

        return AddTransactionViewHolder.CategoryViewHolder(binding)
    }

    private fun createTransactionViewHolder(
        parent: ViewGroup
    ): AddTransactionViewHolder.TransactionViewHolder {

        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val viewHolder = AddTransactionViewHolder.TransactionViewHolder(binding)

        binding.removeCategoryFromRecycler.setOnClickListener {
            val position = viewHolder.adapterPosition
            adapterData.removeAt(position)

            notifyDataSetChanged()
        }

        binding.setDateBtn.setOnClickListener {
            dateClickListener.invoke(viewHolder.adapterPosition)
        }

        binding.amountEditText.doAfterTextChanged {
            val item = adapterData[viewHolder.adapterPosition]
            (item as AddTransactionItem.Transaction).amount = it?.toString() ?: ""
        }

        return viewHolder
    }

    private fun createNewTransactionViewHolder(
        parent: ViewGroup
    ): AddTransactionViewHolder.NewTransactionViewHolder {
        val binding = ItemNewTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AddTransactionViewHolder.NewTransactionViewHolder(binding)
            .also { viewHolder ->
                binding.createNewTransactionBtn.setOnClickListener {
                    val startPosition = viewHolder.adapterPosition

                    adapterData.add(startPosition, AddTransactionItem.Transaction())
                    notifyDataSetChanged()
                }
            }
    }
}
