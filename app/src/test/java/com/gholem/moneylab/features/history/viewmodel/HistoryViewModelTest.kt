package com.gholem.moneylab.features.history.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.features.history.adapter.item.HistoryTransactionItem
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.domain.model.ExcomeCategoryModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import com.gholem.moneylab.features.history.navigation.HistoryNavigationEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

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
    fun `verify invocations on navigateToEditTransaction method call`() = runTest {
        /* When */
        val position = 0L
        viewModel.navigateToEditTransaction(position)

        /* Then */
        assertEquals(
            HistoryNavigationEvent.ToEditTransaction(position),
            viewModel.navigation.value?.getAndForget()
        )
    }
    @Test
    fun `verify invocations on onDoneButtonClick method call`() = runTest {
        /* When */
        viewModel.navigateToEditTransaction(0)

        /* Then */
        viewModel.actions.test {
            assertEquals(AddTransactionViewModel.Action.GetTransactionsData, awaitItem())
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
                    HistoryViewModel.Action.ShowDataHistoryTransactionItem(emptyList()),
                    awaitItem()
                )
                expectNoEvents()
            }
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

    private val historyTransactionItems: List<HistoryTransactionItem> = listOf(
        HistoryTransactionItem.HistoryDate(date = 321L),
        HistoryTransactionItem.HistoryTransaction(
            category = transactionCategory,
            amount = "123",
            1
        )
    )
}