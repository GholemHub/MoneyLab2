package com.gholem.moneylab.features.createNewCategoryImage.viewmodel

import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.features.createNewCategoryImage.navigation.CreateNewCategoryImageEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class CreateNewCategoryImageViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val navigationMock: NavigationLiveData<CreateNewCategoryImageEvent> =
        Mockito.mock(NavigationLiveData::class.java)
                as NavigationLiveData<CreateNewCategoryImageEvent>

    private lateinit var viewModel: CreateNewCategoryImageViewModel

    @Before
    fun setup() {
        viewModel = CreateNewCategoryImageViewModel()
        viewModel.navigation = navigationMock
    }

    @Test
    fun `verify invocations when navigateToPreviousScreen method is called`() = runTest {
        /* When */
        viewModel.navigateToPreviousScreen()

        /* Then */
        Mockito.verify(viewModel.navigation).emit(CreateNewCategoryImageEvent.ToPreviousScreen)
    }
}