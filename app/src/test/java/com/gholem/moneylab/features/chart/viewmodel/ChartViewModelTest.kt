package com.gholem.moneylab.features.chart.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.domain.model.ChartTransactionItem
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class ChartViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getTransactionListUseCaseMock: GetTransactionListUseCase =
        mock(GetTransactionListUseCase::class.java)

    private lateinit var viewModel: ChartViewModel

    @Before
    fun setup() {
        viewModel = ChartViewModel(
            getTransactionListUseCaseMock
        )
    }

    @Test
    fun `verify invocations when fetchTransactionList method is called with transactions`() =
        runTest {
            /* Given */
            `when`(getTransactionListUseCaseMock.run(Unit)).thenReturn(transactionList)

            /* When */
            viewModel.fetchTransactionList()

            /* Then */
            verify(getTransactionListUseCaseMock).run(Unit)
            viewModel.actions.test {
                assertEquals(
                    ChartViewModel.Action.ShowDataChartTransactionItem(chartTransactionItems),
                    awaitItem()
                )
                expectNoEvents()
            }
        }

    @Test
    fun `verify invocations when fetchTransactionList method is called without transactions`() =
        runTest {
            /* Given */
            `when`(getTransactionListUseCaseMock.run(Unit)).thenReturn(emptyList())

            /* When */
            viewModel.fetchTransactionList()

            /* Then */
            verify(getTransactionListUseCaseMock).run(Unit)
            viewModel.actions.test {
                assertEquals(
                    ChartViewModel.Action.ShowDataChartTransactionItem(emptyList()),
                    awaitItem()
                )
                expectNoEvents()
            }
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

    private val chartTransactionItems: List<ChartTransactionItem> = listOf(
        ChartTransactionItem.ChartDate(date = 321L),
        ChartTransactionItem.ChartTransaction(
            category = transactionCategory,
            amount = "123"
        )
    )
}