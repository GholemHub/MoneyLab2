package com.gholem.moneylab.features.chart

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentChartBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.chart.adapter.ChartAdapter
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
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
import java.io.IOException


@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding, ChartViewModel>() {

    lateinit var chartNavigation: ChartNavigation
    private val viewModel: ChartViewModel by viewModels()
    private lateinit var viewBinding: FragmentChartBinding

    private val dataAdapter: ChartAdapter by lazy {
        ChartAdapter(viewModel.adapterData) {
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

    private lateinit var currentMonth: String

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

        var row: Row = sheet.createRow(0)
        val cell: Cell = row.createCell(0)
        cell.setCellValue("Category")
        row.createCell(1).setCellValue(currentMonth)

        list.forEachIndexed { index, transactionModel ->
            val row: Row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(transactionModel.category.categoryName)
            row.createCell(1).setCellValue(transactionModel.amount.toString())
        }
        row = sheet.createRow(list.size + 1)

        var totalExpense = 0
        list.forEach {
            totalExpense += it.amount
        }

        row.createCell(1).setCellValue(totalExpense.toString())

        FileOutputStream(file).use { fileOut -> wb.write(fileOut) }
////////////////////////

//        val launcher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//                if (result.resultCode == Activity.RESULT_OK) {
//                    val intent = result.data
//                    // handle stuff here
//                }
//            }
//
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        val mimeType = arrayOf(
//            "application/msword",
//            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // .doc & .docx
//            "application/vnd.ms-powerpoint",
//            "application/vnd.openxmlformats-officedocument.presentationml.presentation",  // .ppt & .pptx
//            "application/vnd.ms-excel",
//            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",  // .xls & .xlsx
//            "text/plain",
//            "application/pdf",
//            "application/zip"
//        ).joinToString("|")
//        intent.type = mimeType
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        val chooserIntent = Intent
//            .createChooser(intent, "example")
//        launcher.launch(chooserIntent)

        i("THE PASS ${file.path}")
        FileOpen.openFile(requireContext(), file);
        
    }

    object FileOpen {
        @Throws(IOException::class)
        fun openFile(context: Context, url: File) {
            val uri = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName.toString() + ".provider",
                url
            )
            val intent = Intent(Intent.ACTION_VIEW)
            if (url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel")
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent)
        }
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

