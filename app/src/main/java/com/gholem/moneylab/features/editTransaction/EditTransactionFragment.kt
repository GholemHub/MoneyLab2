package com.gholem.moneylab.features.editTransaction

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentEditTransactionBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.features.chooseTransactionCategory.BottomSheetCategoryFragment.Companion.KEY_CATEGORY
import com.gholem.moneylab.features.editTransaction.navigation.EditTransactionNavigation
import com.gholem.moneylab.features.editTransaction.viewmodel.EditTransactionViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import com.gholem.moneylab.util.timestampToString
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber.i
import java.util.*

@AndroidEntryPoint
class EditTransactionFragment :
    BaseFragment<FragmentEditTransactionBinding, EditTransactionViewModel>(), DatePickerDialog.OnDateSetListener {

    lateinit var navigation: EditTransactionNavigation
    private val viewModel: EditTransactionViewModel by viewModels()
    private val args: EditTransactionFragmentArgs by navArgs()
    private lateinit var viewBinding: FragmentEditTransactionBinding
    override fun constructViewBinding(): FragmentEditTransactionBinding =
        FragmentEditTransactionBinding.inflate(layoutInflater)

    override fun init(_viewBinding: FragmentEditTransactionBinding) {
        viewModel.init()
        this.viewBinding = _viewBinding
        setDataOfEditItem(args.position)
        observeActions(viewBinding)
        observeNewCategories()
        viewBinding.amount.doAfterTextChanged {
            var v : String = it.toString()
            if(!v.isEmpty()){
                viewModel.currentTransaction.amount = v.toInt()
            }
        }
        dateSetListener(viewBinding)
        categorySetListener(viewBinding)
        doneBtnSetListener(viewBinding)
        deleteTransactionListener(viewBinding)
    }

    private fun deleteTransactionListener(viewBinding: FragmentEditTransactionBinding) {
        viewBinding.deleteTransaction.setOnClickListener{
            viewModel.deleteTransaction()
        }
    }

    private fun doneBtnSetListener(viewBinding: FragmentEditTransactionBinding) {
        viewBinding.editTransactionDoneBtn.setOnClickListener {
            viewModel.onDoneButtonClick()
        }
    }

    private fun categorySetListener(viewBinding: FragmentEditTransactionBinding) {
        viewBinding.categoryButton.setOnClickListener {
            viewModel.navigateToCategoryBottomSheet()
        }
    }

    private fun observeNewCategories() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(
            KEY_CATEGORY
        )
            ?.observe(viewLifecycleOwner) { result ->
                viewModel.setIdOfCategory(result-1)
            }
    }

    private fun dateSetListener(viewBinding: FragmentEditTransactionBinding) {
        viewBinding.setDateBtn.setOnClickListener {
            showDateDialog()
        }
    }

    override fun setupNavigation() {
        navigation = EditTransactionNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    private fun observeActions(viewBinding: FragmentEditTransactionBinding) {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is EditTransactionViewModel.Action.GetCurrentTransaction -> {
                    fillTransactionData(viewBinding, action.transaction)
                }
                is EditTransactionViewModel.Action.GetCurrentCategory -> {
                    fillCategoryData(viewBinding, action.category)
                }
                EditTransactionViewModel.Action.SetEditedTransaction -> {
                    viewModel.saveEditedTransaction()
                }
            }
        }
    }

    private fun fillTransactionData(viewBinding: FragmentEditTransactionBinding, transaction: TransactionModel) {
        viewBinding.amount.setText(transaction.amount.toString())
        viewBinding.setDateBtn.text = transaction.date.timestampToString()
        viewBinding.categoryButton.text = transaction.category.categoryName
        viewBinding.categoryButton
            .setCompoundDrawablesWithIntrinsicBounds(0, 0, transaction.category.image, 0)
    }

    private fun fillCategoryData(
        viewBinding: FragmentEditTransactionBinding,
        category: TransactionCategoryModel
    ){
        viewBinding.categoryButton.text = category.categoryName
        viewBinding.categoryButton
            .setCompoundDrawablesWithIntrinsicBounds(0, 0, category.image, 0)
        viewModel.currentTransaction.category = category
    }

    private fun setDataOfEditItem(positionItem: Long) {
        viewModel.getTransactionInfo(positionItem)
    }

    private fun showDateDialog() {
        val rightNow: Calendar = Calendar.getInstance()
        var dataPicker = DatePickerDialog(
            requireContext(),
            this,
            rightNow.get(Calendar.YEAR),
            rightNow.get(Calendar.MONTH),
            rightNow.get(Calendar.DAY_OF_MONTH)
        )
        dataPicker.datePicker.maxDate = rightNow.timeInMillis
        dataPicker.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        setDate(p1,p2,p3)
    }

    private fun setDate(year: Int, month: Int, day: Int) {

        val rightNow: Calendar = Calendar.getInstance()
        rightNow.set(Calendar.MILLISECOND, 0)
        rightNow.set(Calendar.SECOND, 0)
        rightNow.set(Calendar.MINUTE, 0)
        rightNow.set(Calendar.HOUR, 0)
        rightNow.set(Calendar.DAY_OF_MONTH, day)
        rightNow.set(Calendar.MONTH, month)
        rightNow.set(Calendar.YEAR, year)

        viewModel.currentTransaction.date = rightNow.timeInMillis
        viewBinding.setDateBtn.text = rightNow.timeInMillis.timestampToString()
    }
}