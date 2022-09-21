package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.domain.model.TransactionCategoryModel
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
        val category = TransactionCategoryModel("1", 2, 3)
        val item = listOf(
            TransactionModel(category, 1, 2,1),
            TransactionModel(category, 3, 4,1)
        )
        `when`(transactionStorageRepositoryMock.insertList(item)).thenReturn(Unit)

        /* When */
        useCase.run(item)

        /* Then */
        verify(transactionStorageRepositoryMock).insertList(item)
    }
}