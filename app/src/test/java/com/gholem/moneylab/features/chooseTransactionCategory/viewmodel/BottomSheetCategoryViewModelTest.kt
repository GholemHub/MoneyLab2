package com.gholem.moneylab.features.chooseTransactionCategory.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.navigation.BottomSheetCategoryEvent
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
class BottomSheetCategoryViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getCategoryListUseCaseMock: GetCategoryListUseCase =
        Mockito.mock(GetCategoryListUseCase::class.java)
    private val navigationMock: NavigationLiveData<BottomSheetCategoryEvent> =
        Mockito.mock(NavigationLiveData::class.java)
                as NavigationLiveData<BottomSheetCategoryEvent>

    private lateinit var viewModel: BottomSheetCategoryViewModel

    @Before
    fun setup() {
        viewModel = BottomSheetCategoryViewModel(
            getCategoryListUseCaseMock
        )
        viewModel.navigation = navigationMock
    }

    @Test
    fun `verify invocations when navigateToAddTransaction method is called`() = runTest {
        /* When */
        viewModel.navigateToAddTransaction()

        /* Then */
        verify(viewModel.navigation).emit(BottomSheetCategoryEvent.ToPreviousScreen)
    }

    @Test
    fun `verify invocations when navigateToCreateNewTransaction method is called`() = runTest {
        /* When */
        viewModel.navigateToCreateNewTransaction()

        /* Then */
        verify(viewModel.navigation).emit(BottomSheetCategoryEvent.ToCreateNewCategory)
    }

    @Test
    fun `verify invocations when getCategory method is called`() = runTest {
        /* Given */
        val transactionCategory = TransactionCategory(
            "123",
            1
        )
        `when`(getCategoryListUseCaseMock.run(Unit)).thenReturn(
            listOf(transactionCategory)
        )

        /* When */
        viewModel.getCategories()

        /* Then */
        verify(getCategoryListUseCaseMock).run(Unit)
        viewModel.actions.test {
            assertEquals(
                BottomSheetCategoryViewModel.Action.ShowData(
                    listOf(transactionCategory)
                ), awaitItem()
            )
            expectNoEvents()
        }
    }
}