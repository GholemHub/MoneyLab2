package com.gholem.moneylab.repository.network.fake

import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.repository.network.TemplateApiRepository

class TemplateApiFakeRepository : TemplateApiRepository {

    override suspend fun getTemplate(): TemplateModel =
        TemplateModel("Mocked data", 1)

    override suspend fun saveTemplate(templateModel: TemplateModel) = Unit
}