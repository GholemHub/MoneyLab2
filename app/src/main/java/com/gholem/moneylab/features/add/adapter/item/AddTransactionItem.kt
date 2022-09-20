package com.gholem.moneylab.features.add.adapter.item

import com.gholem.moneylab.R
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import java.util.*

sealed class AddTransactionItem {
    data class Category(
        var category: TransactionCategoryModel
    ) : AddTransactionItem()

    data class Transaction(
        var amount: String = "",
        var date: Long = Calendar.getInstance().apply {
            this.set(Calendar.MILLISECOND, 0)
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MINUTE, 0)
            this.set(Calendar.HOUR, 0)
        }.timeInMillis,
        val id: Long = System.currentTimeMillis()
    ) : AddTransactionItem()

    object NewTransaction : AddTransactionItem()

    companion object {
        fun getDefaultItems(): List<AddTransactionItem> =
            listOf(
                Category(TransactionCategoryModel("Others", R.drawable.ic_category_other)),
                Transaction(),
                NewTransaction
            )
    }
}