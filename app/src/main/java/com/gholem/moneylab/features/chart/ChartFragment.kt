package com.gholem.moneylab.features.chart

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
        viewBinding.monthChartText.text = viewModel.sumCountMonth()
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
            }
        }
    }
}

