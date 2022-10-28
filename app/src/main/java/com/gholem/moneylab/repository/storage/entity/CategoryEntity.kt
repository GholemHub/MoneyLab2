package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.CategoryItem.ExpenseCategoryModel
import com.gholem.moneylab.domain.model.CategoryItem.IncomeCategoryModel

@Entity(tableName = "category_table")
data class CategoryEntity(
    val name: String,
    val image: Int,
    val type: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {

    fun toModel(): CategoryItem {
        if (type == 1) {
            return ExpenseCategoryModel(
                categoryName = name,
                image = image,
                id = id
            )
        } else {
            return IncomeCategoryModel(
                categoryName = name,
                image = image,
                id = id
            )
        }
    }

    companion object {
        fun from(category: CategoryItem): CategoryEntity {
            return when (category) {
                is ExpenseCategoryModel -> {
                    CategoryEntity(
                        category.categoryName,
                        category.image,
                        1
                    )
                }
                is IncomeCategoryModel -> {
                    CategoryEntity(
                        category.categoryName,
                        category.image,
                        2
                    )
                }
            }
        }
    }
}