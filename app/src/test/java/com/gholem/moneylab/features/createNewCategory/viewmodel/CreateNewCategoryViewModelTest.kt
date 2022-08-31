package com.gholem.moneylab.features.createNewCategory.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import com.gholem.moneylab.features.createNewCategory.navigation.CreateNewCategoryNavigationEvent
import com.gholem.moneylab.features.createNewCategory.navigation.CreateNewCategoryNavigation
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class CreateNewCategoryViewModelTest {
    private lateinit var viewModel: CreateNewCategoryViewModel
    private lateinit var createNewCategoryNavigation: CreateNewCategoryNavigation

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private val insertCategoryModelUseCaseMock: InsertCategoryModelUseCase =
        Mockito.mock(InsertCategoryModelUseCase::class.java)

    private val navigationMock: NavigationLiveData<CreateNewCategoryNavigationEvent> =
        Mockito.mock(NavigationLiveData::class.java)
                as NavigationLiveData<CreateNewCategoryNavigationEvent>

    @Before
    fun setup() {
        viewModel = CreateNewCategoryViewModel(
            insertCategoryModelUseCaseMock
        )
        viewModel.navigation = navigationMock
    }

    //TODO To review
    @Test
    fun `navigateToCreateNewTransaction trigger`() = runTest {
        /* Given */

        /* When */
        viewModel.navigateToImagePicker()

        /* Then */
        Mockito.verify(viewModel.navigation).emit(CreateNewCategoryNavigationEvent.ToImagePicker)
    }

    @Test
    fun `saveCategoryAndFinish trigger`() = runTest {
        /*Given*/
        val transactionCategory = TransactionCategory("123", 1)
        `when`(insertCategoryModelUseCaseMock.run(transactionCategory)).thenReturn(1L)

        /*When*/
        viewModel.saveCategoryAndFinish(transactionCategory)

        /*Then*/
        Mockito.verify(insertCategoryModelUseCaseMock).run(transactionCategory)
        viewModel.actions.test {
            assertEquals(
                CreateNewCategoryViewModel.Action.ReturnCategoryId(1L), awaitItem()
            )
        }
    }
}