package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateTransactionModelUseCaseTest {
    private val transactionStorageRepositoryMock =
        Mockito.mock(TransactionStorageRepository::class.java)

    private var useCase = UpdateTransactionModelUseCase(
        transactionStorageRepositoryMock
    )

    @Test
    fun `verify invocations when run method was called`() = runTest {
        /* Given */
        val item = TransactionCategory("Name", 0, 0)
        Mockito.`when`(transactionStorageRepositoryMock.update(transactionList.first(), 1))
            .thenReturn(Unit)
        /* When */
        val result = useCase.BiConsumer(transactionList.first(), 1)
        /* Then */
        Mockito.verify(transactionStorageRepositoryMock).update(transactionList.first(), 1)
    }

    private val transactionCategory = TransactionCategory(
        categoryName = "categoryName",
        image = 1,
        id = 5
    )
    private val transactionList = listOf(
        Transaction(
            category = transactionCategory,
            amount = 123,
            date = 321
        )
    )
}