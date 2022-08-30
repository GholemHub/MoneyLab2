package com.gholem.moneylab.features.add.viewmodel

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.gholem.moneylab.MainCoroutineRule
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.domain.InsertTransactionsModelUseCase
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.repository.storage.MoneyLabDatabase
import com.gholem.moneylab.repository.storage.dao.TransactionDao
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AddTransactionViewModelTest {
    private lateinit var viewModel: AddTransactionViewModel
    private lateinit var datebase: MoneyLabDatabase
    private lateinit var dao: TransactionDao

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val insertTransactionsModelUseCaseMock: InsertTransactionsModelUseCase =
        mock(InsertTransactionsModelUseCase::class.java)
    private val bottomNavigationVisibilityBusMock: BottomNavigationVisibilityBus =
        mock(BottomNavigationVisibilityBus::class.java)
    private val getCategoryListUseCaseMock: GetCategoryListUseCase =
        mock(GetCategoryListUseCase::class.java)
    private val navigationMock: NavigationLiveData<AddNavigationEvent> =
        mock(NavigationLiveData::class.java) as NavigationLiveData<AddNavigationEvent>


    @Before
    fun setup() {
        viewModel = AddTransactionViewModel(
            insertTransactionsModelUseCaseMock,
            bottomNavigationVisibilityBusMock,
            getCategoryListUseCaseMock
        )
        viewModel.navigation = navigationMock
        datebase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoneyLabDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = datebase.transactionDao()

    }

    @After
    fun teardown() {
        datebase.close()
    }

    //TODO need review
    @Test
    fun `navigateToCategoryBottomSheet trigger`() = runTest {
        /* Given */

        /* When */
        viewModel.navigateToCategoryBottomSheet()

        /* Then */
        Mockito.verify(viewModel.navigation).emit(AddNavigationEvent.ToCategoryBottomSheetDialog)
    }

    //TODO need review
    @Test
    fun `saveTransaction trigger`() = runTest {
        /* Given */
        val transactionEntity = listOf(TransactionEntity(1, 2,1))
        dao.insert(listOf(TransactionEntity(1, 2)))

        `when`(
            insertTransactionsModelUseCaseMock.run(
                listOf(Transaction(TransactionCategory("1", 2, 3), 1, 2))
            )
        )
            .thenReturn(Unit)
        /* When */
        viewModel.saveTransaction(listOf(Transaction(TransactionCategory("1", 2, 3), 1, 2)))

        /* Then */
        assertEquals(dao.getAll(), transactionEntity)
        verify(viewModel.navigation).emit(AddNavigationEvent.ToPreviousScreen)
    }

    //TODO Done need review
    @Test
    fun `onDoneButtonClick trigger`() = runTest {
        /* Given */

        /* When */
        viewModel.onDoneButtonClick()

        /* Then */
        viewModel.actions.test {
            assertEquals(
                AddTransactionViewModel.Action.GetTransactionsData, awaitItem()
            )
        }
    }

    @Test
    fun `init updateCategories trigger`() = runTest {
        /* Given */
        `when`(getCategoryListUseCaseMock.run(Unit)).thenReturn(
            listOf(
                TransactionCategory("123", 123, 123)
            )
        )
        /* When */
        viewModel.init()

        /* Then */
        verify(bottomNavigationVisibilityBusMock).changeVisibility(false)
        verify(getCategoryListUseCaseMock).run(Unit)
        viewModel.actions.test {
            assertEquals(
                AddTransactionViewModel.Action.ShowData(
                    listOf(
                        TransactionCategory("123", 123, 123)
                    )
                ), awaitItem()
            )
        }
    }

    @Test
    fun `updateList trigger`() = runTest {
        /* Given */ //podajemy to co bnam potrzebno dal testowania(Mocki, itemy, fun, var, val)
        val item = 0L
        //Action.ShowData(listOfCategories).send()
        `when`(getCategoryListUseCaseMock.run(Unit)).thenReturn(
            listOf(
                TransactionCategory("123", 123, 123)
            )
        )
        /* When */ //start testFun
        viewModel.updateList(item)
        /* Then */ //mozna sprawdzic czy poszlo testowanie
        viewModel.actions.test {
            assertEquals(
                AddTransactionViewModel.Action.ShowData(
                    listOf(
                        TransactionCategory("123", 123, 123)
                    )
                ), awaitItem()
            )
            assertEquals(AddTransactionViewModel.Action.SelectCategory(0L), awaitItem())
        }
    }
}