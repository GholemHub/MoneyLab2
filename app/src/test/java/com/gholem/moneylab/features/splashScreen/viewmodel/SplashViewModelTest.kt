package com.gholem.moneylab.features.splashScreen.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoriesModelUseCase
import com.gholem.moneylab.features.splashScreen.navigation.SplashNavigationEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val insertCategoriesModelUseCaseMock =
        mock(InsertCategoriesModelUseCase::class.java)
    private val getCategoryListUseCaseMock = mock(GetCategoryListUseCase::class.java)
    private val bottomNavigationVisibilityBusMock =
        mock(BottomNavigationVisibilityBus::class.java)
    private val navigationMock: NavigationLiveData<SplashNavigationEvent> =
        mock(NavigationLiveData::class.java)
                as NavigationLiveData<SplashNavigationEvent>

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        viewModel = SplashViewModel(
            bottomNavigationVisibilityBusMock,
            getCategoryListUseCaseMock,
            insertCategoriesModelUseCaseMock
        )
        viewModel.navigation = navigationMock
    }

    @Test
    fun `verify invocations when getCategories method is called and there are categories`() =
        runTest {
            /* Given */
            val transactionCategory = mutableListOf(
                TransactionCategoryModel("Others", R.drawable.ic_category_other),
                TransactionCategoryModel("Transport", R.drawable.ic_category_transport),
                TransactionCategoryModel("Food", R.drawable.ic_category_food),
                TransactionCategoryModel("Sport", R.drawable.ic_category_sport)
            )
            `when`(getCategoryListUseCaseMock.run(Unit)).thenReturn(transactionCategory)
            /* When */
            viewModel.getCategoriesAndSetDefault(transactionCategory)

            /* Then */
            verify(getCategoryListUseCaseMock).run(Unit)
        }

    @Test
    fun `verify invocations when getCategories method is called and there are no categories`() =
        runTest {
            /* Given */
            val transactionCategory = mutableListOf(
                TransactionCategoryModel("Others", R.drawable.ic_category_other),
                TransactionCategoryModel("Transport", R.drawable.ic_category_transport),
                TransactionCategoryModel("Food", R.drawable.ic_category_food),
                TransactionCategoryModel("Sport", R.drawable.ic_category_sport)
            )
            `when`(getCategoryListUseCaseMock.run(Unit)).thenReturn(emptyList())
            //`when`(insertCategoriesModelUseCaseMock.run(transactionCategory)).thenReturn(Unit)
            /* When */
            viewModel.getCategoriesAndSetDefault(transactionCategory)

            /* Then */
            verify(getCategoryListUseCaseMock).run(Unit)
            verify(insertCategoriesModelUseCaseMock).run(transactionCategory)
        }

    @Test
    fun `verify uiState when init method is called`() = runTest {
        /* When */
        viewModel.init()

        /* Then */
        viewModel.uiState.test { assertEquals(SplashViewModel.UiState.Loading, awaitItem()) }

        /* When */
        advanceTimeBy(3000)

        /* Then */
        viewModel.uiState.test { assertEquals(SplashViewModel.UiState.Loaded, awaitItem()) }

        /* When */
        advanceTimeBy(1000)

        /* Then */
        viewModel.uiState.test {
            assertEquals(SplashViewModel.UiState.NavigateToDashboard, awaitItem())
        }
    }

    @Test
    fun `verify invocations when goToDashboard method is called`() = runTest {
        /* When */
        viewModel.goToDashboard()

        /* Then */
        verify(bottomNavigationVisibilityBusMock).changeVisibility(true)
        verify(viewModel.navigation).emit(SplashNavigationEvent.ToDashboard)
    }
}