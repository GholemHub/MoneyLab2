package com.gholem.moneylab.features.chart.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.InsertTransactionModelUseCase
import com.gholem.moneylab.features.chart.domain.FetchTransactionModelUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class ChartViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val insertTransactionModelUseCaseMock: InsertTransactionModelUseCase =
        Mockito.mock(InsertTransactionModelUseCase::class.java)
    private val fetchTransactionModelUseCaseMock: FetchTransactionModelUseCase =
        Mockito.mock(FetchTransactionModelUseCase::class.java)
    private val GetTransactionListUseCaseMock: GetTransactionListUseCase =
        Mockito.mock(GetTransactionListUseCase::class.java)

    private lateinit var viewModel: ChartViewModel

    @Before
    fun setup() {
        viewModel = ChartViewModel(
            insertTransactionModelUseCaseMock,
            fetchTransactionModelUseCaseMock,
            GetTransactionListUseCaseMock
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
                    ChartViewModel.Action.ShowTransactions(list),
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