package com.gholem.moneylab.repository.storage

import com.gholem.moneylab.domain.model.TemplateModel

interface TemplateStorageRepository {

    suspend fun insertTemplateModel(templateModel: TemplateModel)

    suspend fun getAll(): List<TemplateModel>
}