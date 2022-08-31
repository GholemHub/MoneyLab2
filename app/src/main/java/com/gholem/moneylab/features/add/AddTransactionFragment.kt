package com.gholem.moneylab.features.add

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAddBinding
import com.gholem.moneylab.features.add.adapter.AddTransactionsAdapter
import com.gholem.moneylab.features.add.navigation.AddTransactionNavigation
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import com.gholem.moneylab.features.chooseTransactionCategory.BottomSheetCategoryFragment.Companion.KEY_CATEGORY
import com.gholem.moneylab.features.createNewCategory.CreateNewCategoryFragment.Companion.KEY_CATEGORY_CHOOSE
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddTransactionFragment : BaseFragment<FragmentAddBinding, AddTransactionViewModel>(),
    DatePickerDialog.OnDateSetListener {

    lateinit var navigation: AddTransactionNavigation

    //ViewModel for parse data into
    private val viewModel: AddTransactionViewModel by viewModels()

    private val dataAdapter: AddTransactionsAdapter by lazy {
        AddTransactionsAdapter({ showCategoryBottomSheet() }, { showDateDialog(it) })
    }

    private var position = 0

    override fun constructViewBinding(): FragmentAddBinding =
        FragmentAddBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentAddBinding) {
        observeActions()
        viewModel.init()

        observeCategoryChange()
        observeNewCategories()

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

    //only: fragment back to fragment\\savedStateHandle
    private fun observeCategoryChange() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(KEY_CATEGORY)
            ?.observe(viewLifecycleOwner) { result -> dataAdapter.setCategory(result) }

    }

    private fun observeNewCategories() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(
            KEY_CATEGORY_CHOOSE
        )
            ?.observe(viewLifecycleOwner) { result ->
                viewModel.updateList(result)
            }
    }

    private fun showDateDialog(_position: Int) {
        position = _position

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

    private fun showCategoryBottomSheet() {
        viewModel.navigateToCategoryBottomSheet()
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                AddTransactionViewModel.Action.GetTransactionsData -> {
                    viewModel.saveTransaction(dataAdapter.getTransactionListData())
                }
                is AddTransactionViewModel.Action.ShowData -> {
                    dataAdapter.updateData(action.list)
                }
                is AddTransactionViewModel.Action.SelectCategory -> {
                    dataAdapter.setCategory(action.categoryId)
                }
            }
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        dataAdapter.setDate(position, p3, p2, p1)
    }
}