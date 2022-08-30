package com.gholem.moneylab.features.createNewCategory.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import com.gholem.moneylab.features.createNewCategory.navigaion.CreateNewCategoryEvent
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
class CreateNewCategoryViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val insertCategoryModelUseCaseMock: InsertCategoryModelUseCase =
        Mockito.mock(InsertCategoryModelUseCase::class.java)
    private val navigationMock: NavigationLiveData<CreateNewCategoryEvent> =
        Mockito.mock(NavigationLiveData::class.java)
                as NavigationLiveData<CreateNewCategoryEvent>

    private lateinit var viewModel: CreateNewCategoryViewModel

    @Before
    fun setup() {
        viewModel = CreateNewCategoryViewModel(
            insertCategoryModelUseCaseMock
        )
        viewModel.navigation = navigationMock
    }

    @Test
    fun `verify invocations when navigateToImagePicker method is called`() = runTest {
        /* When */
        viewModel.navigateToImagePicker()

        /* Then */
        verify(viewModel.navigation).emit(CreateNewCategoryEvent.ToImagePicker)
    }

    @Test
    fun `verify invocations when saveCategoryAndFinish method is called`() = runTest {
        /* Given */
        val transactionCategory = TransactionCategory("123", 1)
        `when`(insertCategoryModelUseCaseMock.run(transactionCategory)).thenReturn(1L)

        /* When */
        viewModel.saveCategoryAndFinish(transactionCategory)

        /* Then */
        verify(insertCategoryModelUseCaseMock).run(transactionCategory)
        viewModel.actions.test {
            assertEquals(
                CreateNewCategoryViewModel.Action.ReturnCategoryId(1L), awaitItem()
            )
        }
        verify(viewModel.navigation).emit(CreateNewCategoryEvent.ToPreviousScreen)
    }
}