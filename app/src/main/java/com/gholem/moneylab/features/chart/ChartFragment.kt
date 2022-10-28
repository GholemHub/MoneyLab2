package com.gholem.moneylab.features.chart

import android.content.res.Configuration
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentChartBinding
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.chart.adapter.ChartAdapter
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.gholem.moneylab.features.chart.domain.FileOpen
import com.gholem.moneylab.features.chart.navigation.ChartNavigation
import com.gholem.moneylab.features.chart.viewmodel.ChartViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import timber.log.Timber.i
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding, ChartViewModel>() {

    lateinit var chartNavigation: ChartNavigation
    private val viewModel: ChartViewModel by viewModels()
    private lateinit var viewBinding: FragmentChartBinding
    private lateinit var currentMonth: String

    private val dataAdapter: ChartAdapter by lazy {
        ChartAdapter(viewModel.adapterData, ) {
            saveTransaction(it as ChartItem.Transaction)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.export_to_excel -> {
                viewModel.exportDataToExcel()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_navigation_menu, menu)
    }

    override fun constructViewBinding(): FragmentChartBinding =
        FragmentChartBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentChartBinding) {
        setHasOptionsMenu(true)

        observeActions()
        this.viewBinding = viewBinding
        viewBinding.monthChartText.text = viewModel.nowCountMonth()
        currentMonth = viewModel.nowCountMonth().toString()
        clickListeners()

        viewBinding.chartRecyclerview
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }

    private fun ExportDataToExcel(list: List<TransactionModel>) {

        val letDirectory = File(requireContext().getExternalFilesDir(null), currentMonth)
        letDirectory.mkdirs()
        val wb: Workbook = XSSFWorkbook()
        val currentMonth = currentMonth.replace("/", ".")
        val file = File(letDirectory, "${currentMonth}.xlsx")

        val sheet = wb.createSheet(currentMonth)

        var mainRow: Row = sheet.createRow(0)
        val IncomeCell: Cell = mainRow.createCell(0)
        IncomeCell.setCellValue("Income")
        mainRow.createCell(1).setCellValue(currentMonth)

        val ExpendsCell: Cell = mainRow.createCell(3)
        ExpendsCell.setCellValue("Expends")
        mainRow.createCell(4).setCellValue(currentMonth)
        var expenseIterator = 0
        var incomeIterator = 0

        list.forEachIndexed { i, it ->
            mainRow = sheet.createRow(i + 1)
        }
        var expense = 0
        var income = 0
        list.forEach { transactionModel ->
            var rowIncome: Row = sheet.getRow(incomeIterator + 1)
            var rowExpense: Row = sheet.getRow(expenseIterator + 1)
            rowIncome.createCell(1).setCellValue(income.toString())
            when (transactionModel.category) {
                is CategoryItem.ExpenseCategoryModel -> {
                    expense += transactionModel.amount
                    rowExpense.createCell(3).setCellValue(transactionModel.category.categoryName)
                    rowExpense.createCell(4).setCellValue(transactionModel.amount.toString())
                    expenseIterator++
                }
                is CategoryItem.IncomeCategoryModel -> {
                    income += transactionModel.amount
                    rowIncome.createCell(0).setCellValue(transactionModel.category.categoryName)
                    rowIncome.createCell(1).setCellValue(transactionModel.amount.toString())
                    incomeIterator++
                }
            }
            rowIncome = sheet.getRow(incomeIterator + 1)
            rowIncome.createCell(1).setCellValue(income.toString())

            rowExpense = sheet.getRow(expenseIterator + 1)
            rowExpense.createCell(4).setCellValue(expense.toString())

        }

        FileOutputStream(file).use { fileOut -> wb.write(fileOut) }

        FileOpen.openFile(requireContext(), file);
    }

    private fun clickListeners() {
        viewBinding.previousMonthChart.setOnClickListener {
            val currentMonth = viewModel.deductCountMonth()
            viewBinding.monthChartText.text = currentMonth
            dataAdapter.notifyDataSetChanged()
        }
        viewBinding.nextMonthChart.setOnClickListener {
            val currentMonth = viewModel.sumCountMonth()
            viewBinding.monthChartText.text = currentMonth
            dataAdapter.notifyDataSetChanged()
        }
    }

    override fun setupNavigation() {
        chartNavigation = ChartNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, chartNavigation::navigate)
    }

    private fun saveTransaction(item: ChartItem.Transaction) {
        viewModel.saveNewTransaction(item.transactionModel)
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is ChartViewModel.Action.ShowTransactions -> {
                    dataAdapter.createAdapterData(action.list)
                }
                is ChartViewModel.Action.ExportDataToExcel -> {
                    ExportDataToExcel(action.list)
                }
            }
        }
    }
}