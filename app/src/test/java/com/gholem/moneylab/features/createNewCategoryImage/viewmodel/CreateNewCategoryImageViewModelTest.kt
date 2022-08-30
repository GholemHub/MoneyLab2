package com.gholem.moneylab.features.createNewCategoryImage.viewmodel

import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.features.createNewCategoryImage.navigation.CreateNewCategoryImageEvent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class CreateNewCategoryImageViewModelTest {

    private lateinit var viewModel: CreateNewCategoryImageViewModel

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private val navigationMock: NavigationLiveData<CreateNewCategoryImageEvent> =
        Mockito.mock(NavigationLiveData::class.java)
                as NavigationLiveData<CreateNewCategoryImageEvent>

    @Before
    fun setup() {
        viewModel = CreateNewCategoryImageViewModel()
        viewModel.navigation = navigationMock
    }

    @Test
    fun `navigateToCreateNewTransaction trigger`() = runTest {
        /* Given */

        /* When */
        viewModel.navigateToPreviousScreen()

        /* Then */
        Mockito.verify(viewModel.navigation).emit(CreateNewCategoryImageEvent.ToPreviousScreen)
    }
}