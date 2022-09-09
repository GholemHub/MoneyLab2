package com.gholem.moneylab.repository.network.real

import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.repository.network.TemplateApiRepository
import com.gholem.moneylab.repository.network.api.TemplateApi
import com.gholem.moneylab.repository.network.dto.template.TemplateRequest
import javax.inject.Inject

class TemplateApiNetworkRepository @Inject constructor(
    private val templateApi: TemplateApi
) : TemplateApiRepository {

    override suspend fun getTemplate(): TemplateModel =
        templateApi.getTemplateMethod().toModel()

    override suspend fun saveTemplate(templateModel: TemplateModel) =
        templateApi.saveTemplateMethod(TemplateRequest.fromModel(templateModel))
}