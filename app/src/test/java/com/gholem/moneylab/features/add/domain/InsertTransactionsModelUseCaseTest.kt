package com.gholem.moneylab.features.add.domain

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.repository.storage.TransactionStorageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class InsertTransactionsModelUseCaseTest{
    private val transactionStorageRepositoryMock= Mockito.mock(TransactionStorageRepository::class.java)
    private var useCase = InsertTransactionsModelUseCase(
        transactionStorageRepositoryMock
    )

    @Test
    fun `verify if insert method was triggered`() = runTest {
        /* Given */
        var category = TransactionCategory("1",2,3)
        var item = listOf(
            Transaction(category,1,2),
            Transaction(category,3,4)
        )
        Mockito.`when`(transactionStorageRepositoryMock.insert(item)).thenReturn(Unit)
        /* When */
        val result = useCase.run(item)
        /* Then */
        Assert.assertEquals(Unit,result)
    }
}