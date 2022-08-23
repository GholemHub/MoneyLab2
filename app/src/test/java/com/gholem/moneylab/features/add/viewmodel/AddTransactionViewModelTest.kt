package com.gholem.moneylab.features.add.viewmodel

import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.domain.InsertTransactionsModelUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class AddTransactionViewModelTest {
    private lateinit var viewModel: AddTransactionViewModel

    private val mockInsertTransactionsModelUseCase: InsertTransactionsModelUseCase =
        mock(InsertTransactionsModelUseCase::class.java)
    private val mockBottomNavigationVisibilityBus: BottomNavigationVisibilityBus =
        mock(BottomNavigationVisibilityBus::class.java)
    private val mockGetCategoryListUseCase: GetCategoryListUseCase =
        mock(GetCategoryListUseCase::class.java)

    @Before
    fun setup() {
        viewModel = AddTransactionViewModel(
            mockInsertTransactionsModelUseCase,
            mockBottomNavigationVisibilityBus,
            mockGetCategoryListUseCase
        )
    }

    val TransactionCategory = TransactionCategory(
        "Test",
        123,
        0
    )


    val transactions: List<Transaction> = listOf(
        Transaction(
            TransactionCategory,
            123,
            123
        )
    )

    @Test
    fun `insert `() {
        val v = viewModel.saveTransaction(transactions).isCompleted

        assertThat(v).isTrue()
    }
}