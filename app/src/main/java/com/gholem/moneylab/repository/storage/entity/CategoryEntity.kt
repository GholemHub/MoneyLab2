package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.TransactionCategory

@Entity(tableName = "category_table")
data class CategoryEntity(
    val categoryId: Int,
    val name: String,
    val image: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {

    fun toModel(): TransactionCategory = TransactionCategory(
        id = categoryId,
        categoryName = name,
        image = image
    )

    companion object {
        fun from(category: TransactionCategory): CategoryEntity =
            CategoryEntity(
                category.id,
                category.categoryName,
                category.image
            )
    }

}