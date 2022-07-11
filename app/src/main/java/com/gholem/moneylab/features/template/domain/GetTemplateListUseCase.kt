package com.gholem.moneylab.features.template.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.repository.storage.TemplateStorageRepository
import javax.inject.Inject

class GetTemplateListUseCase @Inject constructor(
    private val repository: TemplateStorageRepository
) : UseCase<Unit, List<TemplateModel>> {

    override suspend fun run(input: Unit): List<TemplateModel> =
        repository.getAll()
}