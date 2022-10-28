package com.gholem.moneylab.features.add.adapter.item

import com.gholem.moneylab.R
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.CategoryItem.ExpenseCategoryModel
import java.util.*

sealed class AddTransactionItem {
    data class Category(
        val category: CategoryItem
    ) : AddTransactionItem()

    data class Transaction(
        val amount: String = "",
        val date: Long = Calendar.getInstance().apply {
            this.set(Calendar.MILLISECOND, 0)
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MINUTE, 0)
            this.set(Calendar.HOUR, 0)
        }.timeInMillis
    ) : AddTransactionItem()

    object NewTransaction : AddTransactionItem()

    companion object {
        fun getDefaultItems(): List<AddTransactionItem> =
            listOf(
                Category(ExpenseCategoryModel("Others", R.drawable.ic_category_other)),
                Transaction(),
                NewTransaction
            )
    }
}