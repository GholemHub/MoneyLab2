package com.gholem.moneylab.repository.network.dto

import com.gholem.moneylab.domain.model.TemplateModel

data class TemplateRequest(
    val name: String
) {

    companion object {
        fun fromModel(templateModel: TemplateModel) =
            TemplateRequest(templateModel.name)
    }
}