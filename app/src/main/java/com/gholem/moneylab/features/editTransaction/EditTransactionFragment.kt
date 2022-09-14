package com.gholem.moneylab.features.editTransaction

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentEditTransactionBinding
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.features.chart.ChartFragment.Companion.EDIT_ITEM
import com.gholem.moneylab.features.editTransaction.viewmodel.EditTransactionViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import com.gholem.moneylab.util.timestampToString

class EditTransactionFragment :
    BaseFragment<FragmentEditTransactionBinding, EditTransactionViewModel>() {

    private val viewModel: EditTransactionViewModel by viewModels()

    override fun constructViewBinding(): FragmentEditTransactionBinding =
        FragmentEditTransactionBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentEditTransactionBinding) {
        //observeActions(viewBinding)
        //setDataOfEditItem(observeEditItem())

    }

    override fun setupNavigation() {

    }

    private fun observeEditItem(): Long {
        var positionItem = 0L
       /* findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(
            EDIT_ITEM
        )
            ?.observe(viewLifecycleOwner) { result ->  positionItem = result }
*/        return positionItem
    }

    private fun observeActions(viewBinding: FragmentEditTransactionBinding) {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is EditTransactionViewModel.Action.GetCurrentTransaction -> {
                    fillData(viewBinding, action.transaction)
                }
            }
        }
    }

    private fun fillData(viewBinding: FragmentEditTransactionBinding, transaction: Transaction) {
        viewBinding.amount.setText(transaction.amount.toString())
        viewBinding.setDateBtn.text = transaction.date.timestampToString()

    }

    private fun setDataOfEditItem(positionItem: Long) {

        viewModel.getTransactionInfo(positionItem)
    }
}