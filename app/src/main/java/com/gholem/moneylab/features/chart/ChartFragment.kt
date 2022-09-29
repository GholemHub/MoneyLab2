package com.gholem.moneylab.features.chart

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentChartBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.chart.adapter.ChartAdapter
import com.gholem.moneylab.features.chart.adapter.ChartAdapterRetrofit
import com.gholem.moneylab.features.chart.navigation.ChartNavigation
import com.gholem.moneylab.features.chart.viewmodel.ChartViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding, ChartViewModel>() {

    lateinit var chartNavigation: ChartNavigation
    private val viewModel: ChartViewModel by viewModels()
    private lateinit var viewBinding: FragmentChartBinding
    private val dataAdapterRetrofit: ChartAdapterRetrofit by lazy {
        ChartAdapterRetrofit { saveTransaction(it) }
    }
    private val dataAdapter: ChartAdapter by lazy {
        ChartAdapter(viewModel.adapterData)
    }

    override fun constructViewBinding(): FragmentChartBinding =
        FragmentChartBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentChartBinding) {
        observeActions()
        initViewModel()
        this.viewBinding = viewBinding
        viewModel.getTransactionList()

        viewBinding.chartRecyclerview
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }

    override fun setupNavigation() {
        chartNavigation = ChartNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, chartNavigation::navigate)
    }

    private fun saveTransaction(item: TransactionModel) {
        viewModel.saveNewTransaction(item)
    }

    private fun initViewModel() {
        viewModel.fetchTransactions()
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is ChartViewModel.Action.ShowTransactionsRetrofit ->
                    dataAdapterRetrofit.setListData(action.transactions)
                is ChartViewModel.Action.ShowTransactions -> {
                    dataAdapter.createAdapterData(action.list)
                }
            }
        }
    }
}
