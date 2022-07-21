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
import com.gholem.moneylab.features.add.navigation.AddTransactionNavigation
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
    }

    override fun setupNavigation() {
        addNavigation = AddTransactionNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, addNavigation::navigate)
    }
}