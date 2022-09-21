package com.gholem.moneylab.features.history.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.features.history.adapter.item.HistoryTransactionItem
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getTransactionListUseCaseMock: GetTransactionListUseCase =
        mock(GetTransactionListUseCase::class.java)

    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setup() {
        viewModel = HistoryViewModel(
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
                    HistoryViewModel.Action.ShowDataChartTransactionItem(historyTransactionItems),
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
                    HistoryViewModel.Action.ShowDataChartTransactionItem(emptyList()),
                    awaitItem()
                )
                expectNoEvents()
            }
        }

    private val transactionCategory = TransactionCategoryModel(
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

    private val historyTransactionItems: List<HistoryTransactionItem> = listOf(
        HistoryTransactionItem.HistoryDate(date = 321L),
        HistoryTransactionItem.HistoryTransaction(
            category = transactionCategory,
            amount = "123",
            1
        )
    )
}