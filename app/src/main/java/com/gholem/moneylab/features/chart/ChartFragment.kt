package com.gholem.moneylab.features.chart

import android.text.format.DateFormat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentChartBinding
import com.gholem.moneylab.features.chart.adapter.ChartAdapter
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.gholem.moneylab.features.chart.navigation.ChartNavigation
import com.gholem.moneylab.features.chart.viewmodel.ChartViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

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

    override fun constructViewBinding(): FragmentChartBinding =
        FragmentChartBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentChartBinding) {
        observeActions()
        this.viewBinding = viewBinding
        viewModel.getTransactionList()
        setMonth()
        clickListeners()

        viewBinding.chartRecyclerview
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }

    private fun clickListeners() {
        viewBinding.previousMonthChart.setOnClickListener {
            viewModel.COUNT_MONTH = viewModel.COUNT_MONTH - viewModel.LAST_MONTH
            setMonth()
            viewModel.getTransactionList()
            dataAdapter.notifyDataSetChanged()
        }
        viewBinding.nextMonthChart.setOnClickListener {
            viewModel.COUNT_MONTH = viewModel.COUNT_MONTH + viewModel.LAST_MONTH
            setMonth()
            viewModel.getTransactionList()
            dataAdapter.notifyDataSetChanged()
        }
    }

    fun convertDate(dateInMilliseconds: String, dateFormat: String?): String? {
        return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
    }

    fun setMonth() {
        var lastMonth = System.currentTimeMillis()
        lastMonth = lastMonth + viewModel.COUNT_MONTH

        viewBinding.monthChartText.text = convertDate(lastMonth.toString(), "MM/yyyy")
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
            }
        }
    }
}

