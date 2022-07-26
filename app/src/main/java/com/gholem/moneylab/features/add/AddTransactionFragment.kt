package com.gholem.moneylab.features.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAddBinding
import com.gholem.moneylab.domain.model.AddTransactionItem
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.adapter.AddTransactionsAdapter
import com.gholem.moneylab.features.add.navigation.AddTransactionNavigation
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionFragment : BaseFragment<FragmentAddBinding, AddTransactionViewModel>() {

    private val viewModel: AddTransactionViewModel by viewModels()

    lateinit var addNavigation: AddTransactionNavigation

    private val dataAdapter: AddTransactionsAdapter by lazy {
        AddTransactionsAdapter()
    }

    override fun constructViewBinding(): FragmentAddBinding =
        FragmentAddBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeActions()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init(viewBinding: FragmentAddBinding) {
        viewModel.init()

        viewBinding.transactionsRecyclerView
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
         viewBinding.doneBtn.setOnClickListener {
             viewModel.onDoneButtonClick()
         }
        dataAdapter.setData(createView())
    }

    override fun setupNavigation() {
        addNavigation = AddTransactionNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, addNavigation::navigate)
    }

    private fun observeActions() {
        viewModel.actions.observe(viewLifecycleOwner) { action ->
            when (action) {
                AddTransactionViewModel.Action.GetData -> {
                    viewModel.saveTransaction(dataAdapter.getData())
                }
            }
        }
    }

    private fun createView() : List<AddTransactionItem> = listOf(
        AddTransactionItem.Category(TransactionCategory.FOOD),
        AddTransactionItem.Transaction(),
        AddTransactionItem.NewTransaction
    )
}