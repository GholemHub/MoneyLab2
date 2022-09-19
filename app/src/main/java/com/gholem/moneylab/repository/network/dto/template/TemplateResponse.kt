package com.gholem.moneylab.repository.network.dto.template

import com.gholem.moneylab.domain.model.TemplateModel

data class TemplateResponse(
    val name: String,
    val count: Int
) {

    fun toModel(): TemplateModel = TemplateModel(name, count)
}