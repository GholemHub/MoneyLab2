package com.gholem.moneylab.features.createNewCategoryImage.viewmodel

import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.arch.nav.NavigationLiveData
<<<<<<< HEAD
import com.gholem.moneylab.features.createNewCategoryImage.navigation.CreateNewCategoryImageNavigationEvent
=======
import com.gholem.moneylab.features.createNewCategoryImage.navigation.CreateNewCategoryImageEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
>>>>>>> d18a1fcf761df6ad1ea6d9b898275b0dbf65fa6e
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class CreateNewCategoryImageViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
<<<<<<< HEAD
    private val navigationMock: NavigationLiveData<CreateNewCategoryImageNavigationEvent> =
=======

    private val navigationMock: NavigationLiveData<CreateNewCategoryImageEvent> =
>>>>>>> d18a1fcf761df6ad1ea6d9b898275b0dbf65fa6e
        Mockito.mock(NavigationLiveData::class.java)
                as NavigationLiveData<CreateNewCategoryImageNavigationEvent>

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
        Mockito.verify(viewModel.navigation).emit(CreateNewCategoryImageNavigationEvent.ToPreviousScreen)
    }
}