package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetTransactionListUseCaseTest {

    private val transactionStorageRepositoryMock = mock(TransactionStorageRepository::class.java)
    private var useCase = GetTransactionListUseCase(transactionStorageRepositoryMock)

    @Test
    fun `verify if getAll method was triggered`() = runTest {
        /* Given */
        val transactionList = listOf(
            TransactionModel(TransactionCategoryModel("categoryName", 1, 5), 2, 3, 1),
            TransactionModel(TransactionCategoryModel("categoryName2", 2, 4), 5, 6, 1)
        )
        `when`(transactionStorageRepositoryMock.getAll()).thenReturn(transactionList)

        /* When */
        val result = useCase.run(Unit)

        /* Then */
        verify(transactionStorageRepositoryMock).getAll()
        assertEquals(2, result.size)
        assertEquals(transactionList.first(), result.first())
        assertEquals(transactionList.last(), result.last())
    }
}