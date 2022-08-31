package com.gholem.moneylab.features.splashScreen.viewmodel

import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import com.gholem.moneylab.features.splashScreen.navigation.SplashNavigationEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {
    private lateinit var viewModel: SplashViewModel

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private val insertCategoryListUseCaseMock = Mockito.mock(InsertCategoryModelUseCase::class.java)
    private val getCategoryListUseCaseMock = Mockito.mock(GetCategoryListUseCase::class.java)
    private val bottomNavigationVisibilityBusMock =
        Mockito.mock(BottomNavigationVisibilityBus::class.java)
    private val navigationMock: NavigationLiveData<SplashNavigationEvent> =
        Mockito.mock(NavigationLiveData::class.java)
                as NavigationLiveData<SplashNavigationEvent>

    @Before
    fun setup() {
        viewModel = SplashViewModel(
            bottomNavigationVisibilityBusMock,
            getCategoryListUseCaseMock,
            insertCategoryListUseCaseMock
        )
        viewModel.navigation = navigationMock
    }

    @Test
    fun `viewModel getCategory trigger`() = runTest {
        /* Given */
        val transactionCategory = mutableListOf(
            TransactionCategory("Others", R.drawable.ic_category_other),
            TransactionCategory(
                "Transport",
                R.drawable.ic_category_transport
            ),
            TransactionCategory( "Food", R.drawable.ic_category_food),
            TransactionCategory( "Sport", R.drawable.ic_category_sport)
        )


        /* When */
        viewModel.getCategory()
        /* Then */
        Assert.assertEquals(getCategoryListUseCaseMock.run(Unit), transactionCategory)
    }

    @Test
    fun `viewModel init trigger`() = runTest {
        /* Given */

        /* When */
        viewModel.init()
        /* Then */
        viewModel.uiState.test {
            Assert.assertEquals(
                SplashViewModel.UiState.Loading, awaitItem()
            )
        }
        advanceTimeBy(3000)
        viewModel.uiState.test {
            Assert.assertEquals(
                SplashViewModel.UiState.Loaded, awaitItem()
            )
        }
        advanceTimeBy(1000)
        viewModel.uiState.test {
            Assert.assertEquals(
                SplashViewModel.UiState.NavigateToDashboard, awaitItem()
            )
        }
    }


    @Test
    fun `init goToDashboard trigger`() = runTest {
        /* Given */

        /* When */
        viewModel.goToDashboard()

        /* Then */
        Mockito.verify(bottomNavigationVisibilityBusMock).changeVisibility(true)
        Mockito.verify(viewModel.navigation).emit(SplashNavigationEvent.ToDashboard)
    }
}