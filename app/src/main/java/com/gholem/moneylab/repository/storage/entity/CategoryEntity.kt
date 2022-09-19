package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.TransactionCategoryModel

@Entity(tableName = "category_table")
data class CategoryEntity(
    val name: String,
    val image: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {

    fun toModel(): TransactionCategoryModel = TransactionCategoryModel(
        categoryName = name,
        image = image,
        id = id
    )

    companion object {
        fun from(category: TransactionCategoryModel): CategoryEntity =
            CategoryEntity(
                category.categoryName,
                category.image
            )
    }
}