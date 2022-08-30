package com.gholem.moneylab.features.template.domain

import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.repository.storage.TemplateStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class GetTemplateListUseCaseTest {

    private val templateStorageRepositoryMock = Mockito.mock(TemplateStorageRepository::class.java)

    private var useCase = GetTemplateListUseCase(
        templateStorageRepositoryMock
    )

    @Test
    fun `verify result and invocations when run method is called`() = runTest {
        /* Given */
        val templateList = listOf(
            TemplateModel("Name", 123),
            TemplateModel("Name2", 1234)

        )
        `when`(templateStorageRepositoryMock.getAll()).thenReturn(templateList)

        /* When */
        val result = useCase.run(Unit)

        /* Then */
        verify(templateStorageRepositoryMock).getAll()
        assertEquals(2, result.size)
        assertEquals(TemplateModel("Name", 123), result.first())
        assertEquals(TemplateModel("Name2", 1234), result.last())
    }
}