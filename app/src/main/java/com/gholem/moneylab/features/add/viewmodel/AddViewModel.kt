package com.gholem.moneylab.features.add.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.AddNextTransaction
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import com.gholem.moneylab.repository.storage.MoneyLabDatabase
import com.gholem.moneylab.repository.storage.TransactionStorageRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus
) : ViewModel() {

    lateinit var readAllData: LiveData<List<TransactionModel>>

    private lateinit var transactionRepository: TransactionStorageRepositoryImpl

    val navigation: NavigationLiveData<AddNavigationEvent> =
        NavigationLiveData()
    //val context: Context


    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun startContext(_context: Context) {
        context = _context
    }

    fun init() {

        bottomNavigationVisibilityBus.changeVisibility(false)
        val transactionDao = MoneyLabDatabase.getDatabase(context).transactionDao()
        transactionRepository = TransactionStorageRepositoryImpl(transactionDao)
        readAllData = transactionDao.getAll()
    }

    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    fun getMockData(): List<AddNextTransaction> = listOf(
        AddNextTransaction.Category(1, "sd11f", 1),
        AddNextTransaction.Transaction(10, "01.02.2000"),
        AddNextTransaction.NewTransaction("Add")
    )
}

