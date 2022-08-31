package com.gholem.moneylab.features.add.adapter.item

import android.content.res.Resources
import com.gholem.moneylab.R
import com.gholem.moneylab.domain.model.TransactionCategory
import java.util.*

sealed class AddTransactionItem {
    data class Category(
        var category: TransactionCategory
    ) : AddTransactionItem()

    data class Transaction(
        var amount: String = "",
        var date: Long = Calendar.getInstance().apply {
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
                Category(TransactionCategory("Others", R.drawable.ic_category_other)),
                Transaction(),
                NewTransaction
            )
    }
}