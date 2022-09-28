package com.gholem.moneylab.features.editTransaction

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentEditTransactionBinding
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.chooseTransactionCategory.BottomSheetCategoryFragment.Companion.KEY_CATEGORY
import com.gholem.moneylab.features.editTransaction.navigation.EditTransactionNavigation
import com.gholem.moneylab.features.editTransaction.viewmodel.EditTransactionViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import com.gholem.moneylab.util.timestampToString
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditTransactionFragment :
    BaseFragment<FragmentEditTransactionBinding, EditTransactionViewModel>(),
    DatePickerDialog.OnDateSetListener {

    lateinit var navigation: EditTransactionNavigation
    private val viewModel: EditTransactionViewModel by viewModels()
    private val args: EditTransactionFragmentArgs by navArgs()
    private lateinit var viewBinding: FragmentEditTransactionBinding
    override fun constructViewBinding(): FragmentEditTransactionBinding =
        FragmentEditTransactionBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentEditTransactionBinding) {
        viewModel.init()
        this.viewBinding = viewBinding
        getTransactionInfo(args.position)
        observeActions(viewBinding)
        observeNewCategories()
        viewBinding.amountEditTextEditTransaction.doAfterTextChanged {
            val amountText: String = it.toString()

            if (!amountText.isEmpty()) {
                viewModel.changeTransactionAmount(amountText.toInt())
                viewBinding.amountInputLayoutEditTransaction.isErrorEnabled = false
            } else {
                viewBinding.amountInputLayoutEditTransaction.error =
                    resources.getText(R.string.error_message)
                viewBinding.amountInputLayoutEditTransaction.isErrorEnabled = true
            }
        }
        attachListeners()
    }

    private fun attachListeners() {
        viewBinding.deleteTransaction.setOnClickListener {
            basicAlert()
        }
        viewBinding.editTransactionDoneBtn.setOnClickListener {
            val editable = viewBinding.amountEditTextEditTransaction.text ?: ""
            viewModel.onDoneButtonClick(editable.toString())
        }
        viewBinding.categoryButton.setOnClickListener {
            viewModel.navigateToCategoryBottomSheet()
        }
        viewBinding.setDateBtn.setOnClickListener {
            showDateDialog()
        }
    }

    private fun observeNewCategories() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(
            KEY_CATEGORY
        )
            ?.observe(viewLifecycleOwner) { result ->
                viewModel.setIdOfCategory(result - 1)
            }
    }

    override fun setupNavigation() {
        navigation = EditTransactionNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    private fun observeActions(viewBinding: FragmentEditTransactionBinding) {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is EditTransactionViewModel.Action.UpdateTransactionInfo -> {
                    fillTransactionData(action.transaction)
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

    private fun fillTransactionData(transaction: TransactionModel) {
        viewBinding.amountEditTextEditTransaction.setText(transaction.amount.toString())
        viewBinding.setDateBtn.text = transaction.date.timestampToString()
        viewBinding.categoryButton.text = transaction.category.categoryName
        viewBinding.categoryButton
            .setCompoundDrawablesWithIntrinsicBounds(0, 0, transaction.category.image, 0)
    }

    private fun fillCategoryData(
        viewBinding: FragmentEditTransactionBinding,
        category: TransactionCategoryModel
    ) {
        viewBinding.categoryButton.text = category.categoryName
        viewBinding.categoryButton
            .setCompoundDrawablesWithIntrinsicBounds(0, 0, category.image, 0)
        viewModel.changeTransactionCategory(category)
    }

    private fun getTransactionInfo(positionItem: Long) {
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
        dataPicker.datePicker.maxDate = viewModel.getTransactionDate()
        dataPicker.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        setDate(p1, p2, p3)
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

        viewModel.changeTransactionDate(rightNow.timeInMillis)
        viewBinding.setDateBtn.text = rightNow.timeInMillis.timestampToString()
    }

    private fun basicAlert() {
        val builder = AlertDialog.Builder(requireContext())

        with(builder)
        {
            setTitle("Delete transaction?")
            setMessage("If you delete you can not load it again")
            setPositiveButton("Delete") { p0, p1 -> viewModel.deleteTransaction() }
            setNegativeButton("Back") { p0, p1 -> }
            show()
        }
    }
}