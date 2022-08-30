package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class GetTransactionListUseCaseTest {
    private val transactionStorageRepositoryMock =
        Mockito.mock(TransactionStorageRepository::class.java)
    private var useCase = GetTransactionListUseCase(
        transactionStorageRepositoryMock
    )

    @Test
    fun `verify if getAll method was triggered`() = runTest {
        /* Given */
        Mockito.`when`(transactionStorageRepositoryMock.getAll()).thenReturn(emptyList())
        /* When */
        useCase.run(Unit)
        /* Then */
        Mockito.verify(transactionStorageRepositoryMock).getAll()
    }

    @Test
    fun `verify if getAll method was used`() = runTest {
        /* Given */
        val transactionList = listOf(
            TransactionEntity(1, 2, 3),
            TransactionEntity(4, 5, 6)
        )
        Mockito.`when`(transactionStorageRepositoryMock.getAll()).thenReturn(transactionList)
        /* When */
        val result = useCase.run(Unit)
        /* Then */
        Assert.assertEquals(2, result.size)
        Assert.assertEquals(
            transactionList.first(), result.first()
        )
        Assert.assertEquals(
            transactionList.last(), result.last()
        )
    }
}