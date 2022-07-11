package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.TemplateModel

@Entity
data class TemplateEntity(
    @PrimaryKey val name: String,
    val count: Int,
) {

    fun toModel(): TemplateModel = TemplateModel(
        name = name,
        count = count
    )

    companion object {

        fun from(templateModel: TemplateModel): TemplateEntity =
            TemplateEntity(
                name = templateModel.name,
                count = templateModel.count
            )
    }
}