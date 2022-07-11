package com.gholem.moneylab.repository.network

import com.gholem.moneylab.domain.model.TemplateModel

interface TemplateApiRepository {

    suspend fun getTemplate(): TemplateModel

    suspend fun saveTemplate(templateModel: TemplateModel)
}