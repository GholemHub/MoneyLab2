package com.gholem.moneylab.features.planning.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.InsertTransactionModelUseCase
import com.gholem.moneylab.features.planning.domain.FetchTransactionModelUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class PlanningViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val insertTransactionModelUseCaseMock: InsertTransactionModelUseCase =
        Mockito.mock(InsertTransactionModelUseCase::class.java)
    private val fetchTransactionModelUseCaseMock: FetchTransactionModelUseCase =
        Mockito.mock(FetchTransactionModelUseCase::class.java)

    private lateinit var viewModel: PlanningViewModel

    @Before
    fun setup() {
        viewModel = PlanningViewModel(
            insertTransactionModelUseCaseMock,
            fetchTransactionModelUseCaseMock
        )
    }

    @Test
    fun `verify invocations when saveNewTransaction method is called`() =
        runTest {
            /* Given */
            `when`(insertTransactionModelUseCaseMock.run(list.first())).thenReturn(Unit)

            /* When */
            viewModel.saveNewTransaction(list.first())

            /* Then */
            verify(insertTransactionModelUseCaseMock).run(list.first())
        }

    @Test
    fun `verify invocations when fetchTransactionList method is called`() =
        runTest {
            /* Given */
            `when`(fetchTransactionModelUseCaseMock.run(Unit)).thenReturn(list)

            /* When */
            viewModel.fetchTransactions()

            /* Then */
            verify(fetchTransactionModelUseCaseMock).run(Unit)
            viewModel.actions.test {
                assertEquals(
                    PlanningViewModel.Action.ShowTransactions(list),
                    awaitItem()
                )
                expectNoEvents()
            }
        }

    private val list: List<TransactionModel> = listOf(
        TransactionModel(TransactionCategoryModel("", 1), 1, 2, 1),
        TransactionModel(TransactionCategoryModel("", 1), 1, 2, 1)
    )
}