package com.gholem.moneylab.features.add.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.InsertTransactionsModelUseCase
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class AddTransactionViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val insertTransactionsModelUseCaseMock: InsertTransactionsModelUseCase =
        mock(InsertTransactionsModelUseCase::class.java)
    private val bottomNavigationVisibilityBusMock: BottomNavigationVisibilityBus =
        mock(BottomNavigationVisibilityBus::class.java)
    private val getCategoryListUseCaseMock: GetCategoryListUseCase =
        mock(GetCategoryListUseCase::class.java)

    private lateinit var viewModel: AddTransactionViewModel

    @Before
    fun setup() {
        viewModel = AddTransactionViewModel(
            insertTransactionsModelUseCaseMock,
            bottomNavigationVisibilityBusMock,
            getCategoryListUseCaseMock
        )
    }

    @Test
    fun `verify invocations on navigateToCategoryBottomSheet method call`() = runTest {
        /* When */
        viewModel.navigateToCategoryBottomSheet()

        /* Then */
        assertEquals(
            AddNavigationEvent.ToCategoryBottomSheetDialog,
            viewModel.navigation.value?.getAndForget()
        )
    }

    @Test
    fun `verify invocations on saveTransaction method call`() = runTest {
        /* Given */
        val transactionList = listOf(
            TransactionModel(
                TransactionCategoryModel("1", 2, 3), 1, 2, 1
            )
        )
        `when`(insertTransactionsModelUseCaseMock.run(transactionList)).thenReturn(Unit)

        /* When */
        viewModel.saveTransaction(transactionList)

        /* Then */
        verify(insertTransactionsModelUseCaseMock).run(transactionList)
        assertEquals(
            AddNavigationEvent.ToCategoryBottomSheetDialog,
            viewModel.navigation.value?.getAndForget()
        )
    }

    @Test
    fun `verify invocations on onDoneButtonClick method call`() = runTest {
        /* When */
        viewModel.onDoneButtonClick()

        /* Then */
        viewModel.actions.test {
            assertEquals(AddTransactionViewModel.Action.GetTransactionsData, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `verify invocations on init method call`() = runTest {
        /* Given */
        `when`(getCategoryListUseCaseMock.run(Unit)).thenReturn(
            listOf(
                TransactionCategoryModel("123", 123, 123)
            )
        )
        /* When */
        viewModel.init()

        /* Then */
        verify(bottomNavigationVisibilityBusMock).changeVisibility(false)
        verify(getCategoryListUseCaseMock).run(Unit)
        viewModel.actions.test {
            assertEquals(
                AddTransactionViewModel.Action.ShowData(
                    listOf(TransactionCategoryModel("123", 123, 123))
                ), awaitItem()
            )
            expectNoEvents()
        }
    }

    @Test
    fun `verify invocations on updateList method call`() = runTest {
        /* Given */
        `when`(getCategoryListUseCaseMock.run(Unit)).thenReturn(
            listOf(
                TransactionCategoryModel("123", 123, 123)
            )
        )

        /* When */
        viewModel.updateList(0L)

        /* Then */
        verify(getCategoryListUseCaseMock).run(Unit)
        viewModel.actions.test {
            assertEquals(
                AddTransactionViewModel.Action.ShowData(
                    listOf(
                        TransactionCategoryModel("123", 123, 123)
                    )
                ), awaitItem()
            )
             assertEquals(AddTransactionViewModel.Action.SelectCategory(0L), awaitItem())
            expectNoEvents()
        }
    }
}