package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class InsertTransactionsModelUseCaseTest {

    private val transactionStorageRepositoryMock = mock(TransactionStorageRepository::class.java)

    private var useCase = InsertTransactionsModelUseCase(transactionStorageRepositoryMock)

    @Test
    fun `verify if insert method was triggered on repository`() = runTest {
        /* Given */
        val category = TransactionCategory("1", 2, 3)
        val item = listOf(
            Transaction(category, 1, 2),
            Transaction(category, 3, 4)
        )
        `when`(transactionStorageRepositoryMock.insert(item)).thenReturn(Unit)

        /* When */
        useCase.run(item)

        /* Then */
        verify(transactionStorageRepositoryMock).insert(item)
    }
}