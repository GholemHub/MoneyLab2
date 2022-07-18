package com.gholem.moneylab.features.add

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAddBinding
import com.gholem.moneylab.features.add.adapter.AddTransactionsAdapter
import com.gholem.moneylab.features.add.navigation.AddNavigation
import com.gholem.moneylab.features.add.viewmodel.AddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel>() {

    private val viewModel: AddViewModel by viewModels()

    lateinit var addNavigation: AddNavigation
    lateinit var adapter: AddTransactionsAdapter

    override fun constructViewBinding(): FragmentAddBinding =
        FragmentAddBinding.inflate(layoutInflater)

    private val dataAdapter: AddTransactionsAdapter by lazy {
        AddTransactionsAdapter()
    }


    override fun init(viewBinding: FragmentAddBinding) {
        viewModel.init()

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