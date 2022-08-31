package com.gholem.moneylab.features.createNewCategoryImage.viewmodel

import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.features.createNewCategory.navigation.CreateNewCategoryNavigationEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateNewCategoryImageViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private lateinit var viewModel: CreateNewCategoryImageViewModel

    @Before
    fun setup() {
        viewModel = CreateNewCategoryImageViewModel()
    }

    @Test
    fun `verify invocations when navigateToPreviousScreen method is called`() = runTest {
        /* When */
        viewModel.navigateToPreviousScreen()

        /* Then */
        Assert.assertEquals(
            CreateNewCategoryNavigationEvent.ToPreviousScreen,
            viewModel.navigation.value?.getAndForget()
        )
    }
}