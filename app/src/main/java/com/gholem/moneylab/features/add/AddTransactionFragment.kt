package com.gholem.moneylab.features.add

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAddBinding
import com.gholem.moneylab.features.add.adapter.AddTransactionsAdapter
import com.gholem.moneylab.features.add.navigation.AddTransactionNavigation
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionFragment : BaseFragment<FragmentAddBinding, AddTransactionViewModel>() {

    private val viewModel: AddTransactionViewModel by viewModels()

    lateinit var addNavigation: AddTransactionNavigation
    lateinit var adapter: AddTransactionsAdapter

    override fun constructViewBinding(): FragmentAddBinding =
        FragmentAddBinding.inflate(layoutInflater)

    private val dataAdapter: AddTransactionsAdapter by lazy {
        AddTransactionsAdapter()
    }


    override fun init(viewBinding: FragmentAddBinding) {
        viewModel.init()
        viewBinding.transactionsRecyclerView
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
        dataAdapter.setData(viewModel.getMockData())
    }

    override fun setupNavigation() {
        addNavigation = AddTransactionNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, addNavigation::navigate)
    }
}