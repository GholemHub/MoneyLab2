package com.gholem.moneylab.features.chooseTransactionCategory.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.domain.model.ExcomeCategoryModel
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
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

    private lateinit var viewModel: BottomSheetCategoryViewModel

    @Before
    fun setup() {
        viewModel = BottomSheetCategoryViewModel(
            getCategoryListUseCaseMock
        )
    }

    @Test
    fun `verify invocations when navigateToAddTransaction method is called`() = runTest {
        /* When */
        viewModel.navigateToAddTransaction()

        /* Then */
        assertEquals(AddNavigationEvent.ToPreviousScreen , viewModel.navigation.value?.getAndForget())
    }

    @Test
    fun `verify invocations when navigateToCreateNewTransaction method is called`() = runTest {
        /* When */
        viewModel.navigateToCreateNewCategory()

        /* Then */
        assertEquals(BottomSheetCategoryEvent.ToCreateNewCategory, viewModel.navigation.value?.getAndForget())
    }

    @Test
    fun `verify invocations when getCategory method is called`() = runTest {
        /* Given */
        val transactionCategory = ExcomeCategoryModel(
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