package com.gholem.moneylab.repository.network.dto

import com.gholem.moneylab.domain.model.TemplateModel

data class TemplateResponse(
    val name: String,
    val count: Int
) {

    fun toModel(): TemplateModel = TemplateModel(name, count)
}