package com.gholem.moneylab.features.chart.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.domain.model.ChartTransactionItem
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class ChartViewModelTest {
    private lateinit var viewModel: ChartViewModel

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private val getTransactionListUseCaseMock: GetTransactionListUseCase =
        mock(GetTransactionListUseCase::class.java)
    private val getCategoryListUseCaseMock: GetCategoryListUseCase =
        mock(GetCategoryListUseCase::class.java)

    @Before
    fun setup() {
        viewModel = ChartViewModel(
            getTransactionListUseCaseMock,
            getCategoryListUseCaseMock
        )
    }

    @Test
    fun `viewModel createRoomDate trigger`() = runTest {
        /* Given */
        val list: List<ChartTransactionItem> = listOf(
            ChartTransactionItem.ChartDate()
        )

        Mockito.`when`(
            getTransactionListUseCaseMock.run(Unit)
        )
            .thenReturn(
                listOf(
                    TransactionEntity(
                        1,
                        123L
                    )
                )
            )

        /* When */
        viewModel.createRoomDate()

        /* Then */
        verify(getCategoryListUseCaseMock).run(Unit)
        viewModel.actions.test {
            Assert.assertEquals(
                ChartViewModel.Action.ShowDataChartTransactionItem(
                    list
                ), awaitItem()
            )
        }
    }

    @Test
    fun `viewModel getCategory trigger`() = runTest {
        /* Given */
        Mockito.`when`(
            getCategoryListUseCaseMock.run(Unit)
        )
            .thenReturn(listOf(TransactionCategory("123", 1)))

        /* When */
        viewModel.getCategory()

        /* Then */
        verify(getCategoryListUseCaseMock).run(Unit)
        viewModel.actions.test {
            Assert.assertEquals(
                ChartViewModel.Action.ShowDataTransactionCategory(
                    listOf(
                        TransactionCategory("123", 1)
                    )
                ), awaitItem()
            )
        }
    }
}