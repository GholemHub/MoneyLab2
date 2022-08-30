package com.gholem.moneylab.features.template.domain

import android.provider.Contacts
import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import com.gholem.moneylab.repository.storage.TemplateStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class InsertTemplateModelUseCaseTest{
    private val templateStorageRepositoryMock= Mockito.mock(TemplateStorageRepository::class.java)

    private var useCase = InsertTemplateModelUseCase(
        templateStorageRepositoryMock
    )

    @Test
    fun `verify if insert method was used`() = runTest {
        /* Given */
        var item = TemplateModel("Name", 123)
        `when`(templateStorageRepositoryMock.insertTemplateModel(item)).thenReturn(Unit)

        /* When */
        useCase.run(item)

        /* Then */
        verify(templateStorageRepositoryMock).insertTemplateModel(item)
    }
}