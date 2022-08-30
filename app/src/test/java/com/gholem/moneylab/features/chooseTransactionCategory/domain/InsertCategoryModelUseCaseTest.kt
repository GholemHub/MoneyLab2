package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class InsertCategoryModelUseCaseTest{
    private val categoryStorageRepositoryMock= mock(CategoryStorageRepository::class.java)

    private var useCase = InsertCategoryModelUseCase(
        categoryStorageRepositoryMock
    )

    @Test
    fun `verify if insert method was used`() = runTest {
        /* Given */
        var item = TransactionCategory("Name",0,0)
        `when`(categoryStorageRepositoryMock.insert(item)).thenReturn(0)

        /* When */
        val result = useCase.run(item)
        /* Then */
        Mockito.verify(categoryStorageRepositoryMock).insert(item)
        assertEquals(0,result)
    }
}