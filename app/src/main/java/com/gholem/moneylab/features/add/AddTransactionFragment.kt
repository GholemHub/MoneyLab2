package com.gholem.moneylab.features.add

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAddBinding
import com.gholem.moneylab.features.add.adapter.AddTransactionsAdapter
import com.gholem.moneylab.features.add.chooseTransactionCategory.BottomSheetCategoryFragment.Companion.KEY_CATEGORY
import com.gholem.moneylab.features.add.navigation.AddTransactionNavigation
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionFragment : BaseFragment<FragmentAddBinding, AddTransactionViewModel>() {

    lateinit var navigation: AddTransactionNavigation

    //ViewModel for parse data into
    private val viewModel: AddTransactionViewModel by viewModels()

    private val dataAdapter: AddTransactionsAdapter by lazy {
        AddTransactionsAdapter {
            showCategoryBottomSheet()
        }
    }

    override fun constructViewBinding(): FragmentAddBinding =
        FragmentAddBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentAddBinding) {
        observeActions()
        viewModel.init()

        observeCategoryChange()

        viewBinding.transactionsRecyclerView
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }

        viewBinding.doneBtn.setOnClickListener {
            viewModel.onDoneButtonClick()
        }
    }

    override fun setupNavigation() {
        navigation = AddTransactionNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    private fun observeCategoryChange() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(KEY_CATEGORY)
            ?.observe(viewLifecycleOwner) { result -> dataAdapter.setCategory(result) }
    }

    private fun showCategoryBottomSheet() {
        viewModel.navigateToCategoryBottomSheet()
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                AddTransactionViewModel.Action.GetTransactionsData -> {
                    viewModel.saveTransaction(dataAdapter.getData())
                }
            }
        }
    }
}