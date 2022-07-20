package com.gholem.moneylab.features.add

import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAddBinding
import com.gholem.moneylab.features.add.adapter.AddTransactionsAdapter
import com.gholem.moneylab.features.add.navigation.AddNavigation
import com.gholem.moneylab.features.add.viewmodel.AddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel>() {

    private val viewModel: AddViewModel by viewModels()
    private lateinit var transactionViewModel: AddViewModel

    lateinit var addNavigation: AddNavigation
    lateinit var adapter: AddTransactionsAdapter

    override fun constructViewBinding(): FragmentAddBinding =
        FragmentAddBinding.inflate(layoutInflater)

    private val dataAdapter: AddTransactionsAdapter by lazy {
        AddTransactionsAdapter()
    }




    override fun init(viewBinding: FragmentAddBinding) {

        transactionViewModel = ViewModelProvider(this).get(transactionViewModel::class.java)

        transactionViewModel.readAllData.observe(this, Observer {transacton ->
            adapter.setDataTransactionModel(transacton)
        })



        context?.let { viewModel.startContext(it) }
        adapter = AddTransactionsAdapter()

        viewBinding.transactionsRecyclerView.adapter = adapter
        viewBinding.transactionsRecyclerView.layoutManager = LinearLayoutManager(context)

        dataAdapter.setData(viewModel.getMockData())

        viewBinding.transactionsRecyclerView
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }



    override fun setupNavigation() {
        addNavigation = AddNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, addNavigation::navigate)
    }
}