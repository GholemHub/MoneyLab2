package com.gholem.moneylab.features.add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ItemAddCategoryBinding
import com.gholem.moneylab.databinding.ItemAddNewTransactionBinding
import com.gholem.moneylab.databinding.ItemAddTransactionBinding
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.CategoryItem.IncomeCategoryModel
import com.gholem.moneylab.domain.model.CategoryItem.ExpenseCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.adapter.item.AddTransactionItem
import com.gholem.moneylab.features.add.adapter.viewholder.AddTransactionViewHolder
import java.util.*

class AddTransactionsAdapter(
    private val adapterData: MutableList<AddTransactionItem>,
    private val categoryClickListener: () -> Unit,
    private val dateClickListener: (position: Int) -> Unit
) :
    RecyclerView.Adapter<AddTransactionViewHolder>() {

    private val MAX_COUNT_TRANSACTIONS = 6

    private var listOfCategory: List<CategoryItem> = mutableListOf()
    private var listOfInvalidItemsIndexes = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTransactionViewHolder {
        return createViewHolders(parent, viewType)
    }

    override fun onBindViewHolder(holder: AddTransactionViewHolder, position: Int) {
        val isInvalidData = listOfInvalidItemsIndexes.contains(position)

        when (holder) {
            is AddTransactionViewHolder.CategoryViewHolder ->
                holder.bind(adapterData[position] as AddTransactionItem.Category)
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
            is AddTransactionItem.Category -> R.layout.item_add_category
            is AddTransactionItem.Transaction -> R.layout.item_add_transaction
            is AddTransactionItem.NewTransaction -> R.layout.item_add_new_transaction
        }
    }

    fun updateData(listOfTrCategory: List<CategoryItem>) {
        listOfCategory = listOfTrCategory
        notifyDataSetChanged()
    }

    fun setDate(
        position: Int,
        day: Int,
        month: Int,
        year: Int,
        hour: Int,
        minute: Int,
        sec: Int,

        ) {
        val transaction = adapterData[position]
                as? AddTransactionItem.Transaction

        val rightNow: Calendar = Calendar.getInstance()
        rightNow.set(Calendar.MILLISECOND, 0)
        rightNow.set(Calendar.SECOND, sec)
        rightNow.set(Calendar.MINUTE, minute)
        rightNow.set(Calendar.HOUR, hour)
        rightNow.set(Calendar.DAY_OF_MONTH, day)
        rightNow.set(Calendar.MONTH, month)
        rightNow.set(Calendar.YEAR, year)

        transaction?.copy(date = rightNow.timeInMillis)?.let {
            adapterData[position] = it
            notifyItemChanged(position)
        }
    }

    fun setCategory(categoryId: Long) {
        val cat = adapterData.first {
            it is AddTransactionItem.Category
        } as AddTransactionItem.Category

        val copyCategory = cat.copy(category = listOfCategory.first {
            when(it){
                is ExpenseCategoryModel -> {
                    it.id == categoryId
                }
                is CategoryItem.IncomeCategoryModel -> {
                    it.id == categoryId
                }
            }

        })
        val categoryPosition = adapterData.indexOf(cat)
        adapterData[categoryPosition] = copyCategory
        notifyItemChanged(adapterData.indexOf(copyCategory))
    }

    fun getTransactionListData(): List<TransactionModel> {
        val listTransactionModel = mutableListOf<TransactionModel>().apply {
            var currentCategory: CategoryItem? = null

            adapterData.forEach { item ->
                when (item) {
                    is AddTransactionItem.Category -> {
                        currentCategory = item.category
                    }
                    is AddTransactionItem.Transaction -> {
                        when(currentCategory){
                            is ExpenseCategoryModel -> {
                                this.add(mapToTransaction(currentCategory as ExpenseCategoryModel, item))
                            }
                            is IncomeCategoryModel -> {
                                this.add(mapToTransaction(currentCategory as IncomeCategoryModel, item))
                            }
                        }
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
        category: CategoryItem,
        item: AddTransactionItem.Transaction
    ) : TransactionModel {
        return when(category){
            is ExpenseCategoryModel -> {
                TransactionModel(
                    category = category,
                    amount = item.amount.ifBlank { "0" }.toInt(),
                    date = item.date,
                    transactionId = 0
                )
            }
            is IncomeCategoryModel -> {
                TransactionModel(
                    category = category,
                    amount = item.amount.ifBlank { "0" }.toInt(),
                    date = item.date,
                    transactionId = 0
                )
            }
        }

    }



    private fun createViewHolders(
        parent: ViewGroup,
        viewType: Int
    ): AddTransactionViewHolder {
        return when (viewType) {
            R.layout.item_add_category -> createCategoryViewHolder(parent)
            R.layout.item_add_transaction -> createTransactionViewHolder(parent)
            R.layout.item_add_new_transaction -> createNewTransactionViewHolder(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    private fun createCategoryViewHolder(
        parent: ViewGroup
    ): AddTransactionViewHolder.CategoryViewHolder {
        val binding = ItemAddCategoryBinding.inflate(
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

        val binding = ItemAddTransactionBinding.inflate(
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
            val copyAmount =
                (item as AddTransactionItem.Transaction).copy(amount = it?.toString() ?: "")
            adapterData[viewHolder.adapterPosition] = copyAmount
        }
        return viewHolder
    }

    private fun createNewTransactionViewHolder(
        parent: ViewGroup
    ): AddTransactionViewHolder.NewTransactionViewHolder {
        val binding = ItemAddNewTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AddTransactionViewHolder.NewTransactionViewHolder(binding)
            .also { viewHolder ->
                binding.createNewTransactionBtn.setOnClickListener {
                    val startPosition = viewHolder.adapterPosition
                    if (startPosition <= MAX_COUNT_TRANSACTIONS) {
                        adapterData.add(startPosition, AddTransactionItem.Transaction())
                    }
                    notifyDataSetChanged()
                }
            }
    }
}
