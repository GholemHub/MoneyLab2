package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
<<<<<<< HEAD
import org.mockito.Mockito
import org.mockito.Mockito.`when`
=======
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
>>>>>>> d18a1fcf761df6ad1ea6d9b898275b0dbf65fa6e
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class GetTransactionListUseCaseTest {
<<<<<<< HEAD
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
=======

    private val transactionStorageRepositoryMock = mock(TransactionStorageRepository::class.java)

    private var useCase = GetTransactionListUseCase(transactionStorageRepositoryMock)
>>>>>>> d18a1fcf761df6ad1ea6d9b898275b0dbf65fa6e

    @Test
    fun `verify if getAll method was triggered`() = runTest {
        /* Given */
        val transactionList = listOf(
            Transaction(TransactionCategory("categoryName", 1, 5), 2, 3),
            Transaction(TransactionCategory("categoryName2", 2, 4), 5, 6)
        )
        `when`(transactionStorageRepositoryMock.getAll()).thenReturn(transactionList)

        /* When */
        val result = useCase.run(Unit)

        /* Then */
<<<<<<< HEAD
        assertEquals(2, result.size)
        assertEquals(
            transactionList.first(), result.first()
        )
        assertEquals(
            transactionList.last(), result.last()
        )
=======
        verify(transactionStorageRepositoryMock).getAll()
        assertEquals(2, result.size)
        assertEquals(transactionList.first(), result.first())
        assertEquals(transactionList.last(), result.last())
>>>>>>> d18a1fcf761df6ad1ea6d9b898275b0dbf65fa6e
    }
}