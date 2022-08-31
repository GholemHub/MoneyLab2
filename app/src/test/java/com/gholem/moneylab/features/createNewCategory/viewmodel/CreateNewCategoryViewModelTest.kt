package com.gholem.moneylab.features.createNewCategory.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import com.gholem.moneylab.features.createNewCategory.navigation.CreateNewCategoryNavigationEvent
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

    private lateinit var viewModel: CreateNewCategoryViewModel

    @Before
    fun setup() {
        viewModel = CreateNewCategoryViewModel(
            insertCategoryModelUseCaseMock
        )
    }

    @Test
    fun `verify invocations when navigateToImagePicker method is called`() = runTest {
        /* When */
        viewModel.navigateToImagePicker()

        /* Then */
        assertEquals(CreateNewCategoryNavigationEvent.ToImagePicker, viewModel.navigation.value?.getAndForget())
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
        assertEquals(CreateNewCategoryNavigationEvent.ToPreviousScreen, viewModel.navigation.value?.getAndForget())
    }
}