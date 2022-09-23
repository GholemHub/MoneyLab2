package com.gholem.moneylab.features.editTransaction.viewmodel

import android.text.Editable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.DeleteTransactionModelUseCase
import com.gholem.moneylab.features.add.domain.GetTransactionItemUseCase
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.UpdateTransactionModelUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.editTransaction.navigation.EditTransactionNavigationEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class EditTransactionViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val bottomNavigationVisibilityBusMock: BottomNavigationVisibilityBus =
        Mockito.mock(BottomNavigationVisibilityBus::class.java)
    private val getCategoryListUseCaseMock: GetCategoryListUseCase =
        Mockito.mock(GetCategoryListUseCase::class.java)
    private val getTransactionItemUseCaseMock: GetTransactionItemUseCase =
        Mockito.mock(GetTransactionItemUseCase::class.java)
    private val getTransactionListUseCaseMock: GetTransactionListUseCase =
        Mockito.mock(GetTransactionListUseCase::class.java)
    private val updateTransactionModelUseCaseMock: UpdateTransactionModelUseCase =
        Mockito.mock(UpdateTransactionModelUseCase::class.java)
    private val deleteTransactionModelUseCaseMock: DeleteTransactionModelUseCase =
        Mockito.mock(DeleteTransactionModelUseCase::class.java)

    private lateinit var viewModel: EditTransactionViewModel

    @Before
    fun setup() {
        viewModel = EditTransactionViewModel(
            getCategoryListUseCaseMock,
            getTransactionItemUseCaseMock,
            bottomNavigationVisibilityBusMock,
            updateTransactionModelUseCaseMock,
            deleteTransactionModelUseCaseMock
        )
    }

    @Test
    fun `verify invocations on setCurrentTransaction method call`() = runTest {
        /* When */
        viewModel.setCurrentTransaction(transactionModel)

        /* Then */
        assertEquals(viewModel.setCurrentTransaction(transactionModel), Unit)
    }

    @Test
    fun `verify invocations on changeTransactionDate method call`() = runTest {
        /* Given */
        viewModel.setCurrentTransaction(transactionModel)

        /* When */
        viewModel.changeTransactionDate(1)

        /* Then */
        assertEquals(viewModel.changeTransactionDate(1), Unit)
    }

    @Test
    fun `verify invocations on changeTransactionAmount method call`() = runTest {
        /* Given */
        viewModel.setCurrentTransaction(transactionModel)

        /* When */
        viewModel.changeTransactionAmount(1)

        /* Then */
        assertEquals(viewModel.changeTransactionAmount(1), Unit)
    }

    @Test
    fun `verify invocations on changeTransactionCategory method call`() = runTest {
        /* Given */
        viewModel.setCurrentTransaction(transactionModel)

        /* When */
        viewModel.changeTransactionCategory(transactionCategory)

        /* Then */
        assertEquals(viewModel.changeTransactionCategory(transactionCategory), Unit)
    }

    @Test
    fun `verify invocations on getTransactionDate method call`() = runTest {
        /* Given */
        viewModel.setCurrentTransaction(transactionModel)

        /* When */
        viewModel.getTransactionDate()

        /* Then */
        assertEquals(viewModel.getTransactionDate(), 1)
    }

    @Test
    fun `verify invocations on deleteTransaction method call`() = runTest {
        /* Given */
        viewModel.setCurrentTransaction(transactionModel)
        `when`(deleteTransactionModelUseCaseMock.run(1))
            .thenReturn(Unit)

        /* When */
        viewModel.deleteTransaction()

        /* Then */
        assertEquals(
            EditTransactionNavigationEvent.ToPreviousScreen,
            viewModel.navigation.value?.getAndForget()
        )
    }

    @Test
    fun `verify invocations on onDoneButtonClick method call`() = runTest {
        /* When */
        viewModel.onDoneButtonClick("1")

        /* Then */
        viewModel.actions.test {
            assertEquals(EditTransactionViewModel.Action.SetEditedTransaction, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `verify invocations on editTransaction init method call`() = runTest {
        /* When */
        viewModel.init()

        /* Then */
        verify(bottomNavigationVisibilityBusMock).changeVisibility(false)
    }

    @Test
    fun `verify invocations on saveEditedTransaction method call`() = runTest {
        /* Given */
        `when`(updateTransactionModelUseCaseMock.run(transactionList.first()))
            .thenReturn(Unit)
        viewModel.setCurrentTransaction(TransactionModel(transactionCategory, 123, 321, 1))

        /* When */
        viewModel.saveEditedTransaction()

        /* Then */
        verify(updateTransactionModelUseCaseMock).run(transactionList.first())
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

    @Test
    fun `verify invocations on navigateToCategoryBottomSheet method call`() = runTest {
        /* When */
        viewModel.navigateToCategoryBottomSheet()

        /* Then */
        assertEquals(
            EditTransactionNavigationEvent.ToCategoryBottomSheetDialog,
            viewModel.navigation.value?.getAndForget()
        )
    }

    @Test
    fun `verify invocations on getTransactionInfo method call`() = runTest {
        /* Given */
        `when`(getTransactionListUseCaseMock.run(Unit)).thenReturn(transactionList)
        /* When */
        viewModel.getTransactionInfo(0)

        /* Then */
        verify(getTransactionListUseCaseMock).run(Unit)
        viewModel.actions.test {
            assertEquals(
                EditTransactionViewModel.Action.UpdateTransactionInfo(
                    TransactionModel(
                        transactionCategory,
                        123,
                        321,
                        1
                    )
                ),
                awaitItem()
            )
            expectNoEvents()
        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

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
    private val transactionModel = TransactionModel(transactionCategory, 1, 1, 1)
}