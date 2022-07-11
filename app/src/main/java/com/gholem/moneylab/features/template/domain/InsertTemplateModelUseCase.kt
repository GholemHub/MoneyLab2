package com.gholem.moneylab.features.template.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.repository.storage.TemplateStorageRepository
import javax.inject.Inject

class InsertTemplateModelUseCase @Inject constructor(
    private val templateStorageRepository: TemplateStorageRepository
) : UseCase<TemplateModel, Unit> {

    override suspend fun run(input: TemplateModel) {
        templateStorageRepository.insertTemplateModel(input)
    }
}