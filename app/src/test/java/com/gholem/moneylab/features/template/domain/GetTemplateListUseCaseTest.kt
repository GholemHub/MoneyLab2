package com.gholem.moneylab.features.template.domain

import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.repository.storage.TemplateStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class GetTemplateListUseCaseTest {
    private val templateStorageRepositoryMock = Mockito.mock(TemplateStorageRepository::class.java)

    private var useCase = GetTemplateListUseCase(
        templateStorageRepositoryMock
    )

    @Test
    fun `verify if getAll method was triggered`() = runTest {
        /* Given */
        Mockito.`when`(templateStorageRepositoryMock.getAll()).thenReturn(emptyList())

        /* When */
        useCase.run(Unit)

        /* Then */
        //Chech if it is used
        Mockito.verify(templateStorageRepositoryMock).getAll()
    }

    @Test
    fun `verify if getAll method was used`() = runTest {
        /* Given */
        val templateList = listOf(
            TemplateModel("Name", 123),
            TemplateModel("Name2", 1234)

        )
        Mockito.`when`(templateStorageRepositoryMock.getAll()).thenReturn(templateList)

        /* When */
        val result = useCase.run(Unit)

        /* Then */
        assertEquals(2, result.size)

        assertEquals(
            TemplateModel("Name", 123), result.first()
        )
        assertEquals(
            TemplateModel("Name2", 1234), result.last()
        )

    }
}