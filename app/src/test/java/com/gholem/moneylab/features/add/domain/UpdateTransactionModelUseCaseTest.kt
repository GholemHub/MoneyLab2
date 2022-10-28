package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.domain.model.ExcomeCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
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
        val item = ExcomeCategoryModel("Name", 0, 0)
        Mockito.`when`(transactionStorageRepositoryMock.updateItem(transactionList.first()))
            .thenReturn(Unit)
        /* When */
        val result = useCase.run(transactionList.first())
        /* Then */
        Mockito.verify(transactionStorageRepositoryMock).updateItem(transactionList.first())
        Mockito.verify(transactionStorageRepositoryMock).updateItem(transactionList.first())
    }

    private val transactionCategory = ExcomeCategoryModel(
        categoryName = "categoryName",
        image = 1,
        id = 5
    )
    private val transactionList = listOf(
        TransactionModel(
            category = transactionCategory,
            amount = 123,
            date = 321,
            1
        )
    )
}