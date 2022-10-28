package com.gholem.moneylab.features.chart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.domain.model.ExcomeCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.InsertTransactionModelUseCase
import com.gholem.moneylab.features.chart.domain.FetchTransactionModelUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class ChartViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val insertTransactionModelUseCaseMock: InsertTransactionModelUseCase =
        Mockito.mock(InsertTransactionModelUseCase::class.java)
    private val fetchTransactionModelUseCaseMock: FetchTransactionModelUseCase =
        Mockito.mock(FetchTransactionModelUseCase::class.java)
    private val getTransactionListUseCaseMock: GetTransactionListUseCase =
        Mockito.mock(GetTransactionListUseCase::class.java)

    private lateinit var viewModel: ChartViewModel

    @Before
    fun setup() {
        viewModel = ChartViewModel(
            insertTransactionModelUseCaseMock,
            fetchTransactionModelUseCaseMock,
            getTransactionListUseCaseMock
        )
    }

    @Test
    fun `verify invocations when getTransactionList method is called`() =
        runTest {
            /* Given */
            `when`(getTransactionListUseCaseMock.run(Unit)).thenReturn(list)
            /* When */
            viewModel.getTransactionList()

            /* Then */
            viewModel.actions.test {
                assertEquals(
                    ChartViewModel.Action.ShowTransactions(chartCategoryModelList),
                    awaitItem()
                )
                expectNoEvents()
            }
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

    private val chartCategoryModelList: List<TransactionModel> = listOf(
        TransactionModel(ExcomeCategoryModel("", 1), 1, 1, 1)
    )

    private val list: List<TransactionModel> = listOf(
        TransactionModel(ExcomeCategoryModel("", 1), 1, 1664790573068, 1),
        TransactionModel(ExcomeCategoryModel("", 1), 1, 1664790573068, 1)
    )
}