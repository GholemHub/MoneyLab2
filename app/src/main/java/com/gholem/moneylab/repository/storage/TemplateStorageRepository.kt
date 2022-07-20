package com.gholem.moneylab.repository.storage

import androidx.lifecycle.LiveData
import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

interface TemplateStorageRepository {

    suspend fun insertTemplateModel(templateModel: TemplateModel)

    suspend fun getAll(): List<TemplateModel>
}