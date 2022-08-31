package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

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
        `when`(transactionStorageRepositoryMock.getAll()).thenReturn(emptyList())

        /* When */
        useCase.run(Unit)

        /* Then */
        verify(transactionStorageRepositoryMock).getAll()
    }

    @Test
    fun `verify if getAll method was used`() = runTest {
        /* Given */
        val transactionList = listOf(
            TransactionEntity(1, 2, 3),
            TransactionEntity(4, 5, 6)
        )
        `when`(transactionStorageRepositoryMock.getAll()).thenReturn(transactionList)

        /* When */
        val result = useCase.run(Unit)
        /* Then */
        assertEquals(2, result.size)
        assertEquals(
            transactionList.first(), result.first()
        )
        assertEquals(
            transactionList.last(), result.last()
        )
    }
}