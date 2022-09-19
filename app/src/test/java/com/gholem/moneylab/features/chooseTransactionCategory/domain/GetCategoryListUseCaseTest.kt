package com.gholem.moneylab.features.chooseTransactionCategory.domain

import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.repository.storage.CategoryStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetCategoryListUseCaseTest {

    private val categoryStorageRepositoryMock = mock(CategoryStorageRepository::class.java)

    private var useCase = GetCategoryListUseCase(
        categoryStorageRepositoryMock
    )

    @Test
    fun `verify invocations when run method is called`() = runTest {
        /* Given */
        `when`(categoryStorageRepositoryMock.getAll()).thenReturn(emptyList())

        /* When */
        useCase.run(Unit)

        /* Then */
        verify(categoryStorageRepositoryMock).getAll()
    }

    @Test
    fun `verify result if run method is called`() = runTest {
        /* Given */
        val categoryList = listOf(
            TransactionCategoryModel(
                categoryName = "Cat1",
                image = 0
            ),
            TransactionCategoryModel(
                categoryName = "Cat2",
                image = 1
            )
        )
        `when`(categoryStorageRepositoryMock.getAll()).thenReturn(categoryList)

        /* When */
        val result = useCase.run(Unit)

        /* Then */
        assertEquals(2, result.size)

        assertEquals(
            TransactionCategoryModel(
                categoryName = "Cat1",
                image = 0
            ), result.first()
        )
        assertEquals(
            TransactionCategoryModel(
                categoryName = "Cat2",
                image = 1
            ), result.last()
        )
    }
}