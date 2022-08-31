package com.gholem.moneylab.features.template.domain

import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.repository.storage.TemplateStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class InsertTemplateModelUseCaseTest {

    private val templateStorageRepositoryMock = Mockito.mock(TemplateStorageRepository::class.java)

    private var useCase = InsertTemplateModelUseCase(
        templateStorageRepositoryMock
    )

    @Test
    fun `verify invocations when run method is called`() = runTest {
        /* Given */
        val item = TemplateModel("Name", 123)
        `when`(templateStorageRepositoryMock.insertTemplateModel(item)).thenReturn(Unit)

        /* When */
        useCase.run(item)

        /* Then */
        verify(templateStorageRepositoryMock).insertTemplateModel(item)
    }
}