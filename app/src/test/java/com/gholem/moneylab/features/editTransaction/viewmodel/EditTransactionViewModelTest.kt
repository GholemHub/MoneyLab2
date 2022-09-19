package com.gholem.moneylab.features.editTransaction.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.UpdateTransactionModelUseCase
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class EditTransactionViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val bottomNavigationVisibilityBusMock: BottomNavigationVisibilityBus =
        Mockito.mock(BottomNavigationVisibilityBus::class.java)
    private val getCategoryListUseCaseMock: GetCategoryListUseCase =
        Mockito.mock(GetCategoryListUseCase::class.java)
    private val getTransactionListUseCaseMock: GetTransactionListUseCase =
        Mockito.mock(GetTransactionListUseCase::class.java)
    private val updateTransactionModelUseCaseMock: UpdateTransactionModelUseCase =
        Mockito.mock(UpdateTransactionModelUseCase::class.java)


    private lateinit var viewModel: EditTransactionViewModel

    @Before
    fun setup() {
        viewModel = EditTransactionViewModel(
            getCategoryListUseCaseMock,
            getTransactionListUseCaseMock,
            bottomNavigationVisibilityBusMock,
            updateTransactionModelUseCaseMock
        )
    }

    @Test
    fun `verify invocations on getTransaction method call`() = runTest {
        /* Given */

        /* When */
        viewModel.init()

        /* Then */
        verify(bottomNavigationVisibilityBusMock).changeVisibility(false)
    }

    @Test
    fun `verify invocations on onDoneButtonClick method call`() = runTest {
        /* When */
        viewModel.onDoneButtonClick()

        /* Then */
        viewModel.actions.test {
            assertEquals(EditTransactionViewModel.Action.SetEditedTransaction, awaitItem())
            expectNoEvents()
        }
    }
    ///???? HOW TO MOCK LATEINIT VAR
    @Test
    fun `verify invocations on saveEditedTransaction method call`() = runTest {

        /* Given */
        `when`(updateTransactionModelUseCaseMock.BiConsumer(transactionList.first(), 1))
            .thenReturn(Unit)
        /* When */
        viewModel.saveEditedTransaction()

        /* Then */
        verify(updateTransactionModelUseCaseMock).BiConsumer(transactionList.first(), 1)
    }

    @Test
    fun `verify invocations on setIdOfCategory method call`() = runTest {
        /* Given */
        `when`(getCategoryListUseCaseMock.run(Unit)).thenReturn(listOf(transactionCategory))
        /* When */
        viewModel.setIdOfCategory(0)

        /* Then */
        verify(getCategoryListUseCaseMock).run(Unit)
        viewModel.actions.test {
            assertEquals(
                EditTransactionViewModel.Action.GetCurrentCategory(transactionCategory),
                awaitItem()
            )
            expectNoEvents()
        }
    }

    ///???? IS IT WORKING GOOD?
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
    fun `verify invocations on init method call`() = runTest {
        /* Given */
        `when`(getTransactionListUseCaseMock.run(Unit)).thenReturn(transactionList)
        /* When */
        viewModel.getTransactionInfo(0)

        /* Then */
        verify(getTransactionListUseCaseMock).run(Unit)
        viewModel.actions.test {
            assertEquals(
                EditTransactionViewModel.Action.GetCurrentTransaction(
                    Transaction(
                        transactionCategory,
                        123,
                        321
                    )
                ),
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
}