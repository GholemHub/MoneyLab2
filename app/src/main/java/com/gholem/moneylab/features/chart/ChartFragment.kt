package com.gholem.moneylab.features.chart

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentChartBinding
import com.gholem.moneylab.features.chart.adapter.ChartAdapter
import com.gholem.moneylab.features.chart.navigation.ChartNavigation
import com.gholem.moneylab.features.chart.viewmodel.ChartViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding,ChartViewModel>() {

    private val viewModel: ChartViewModel by viewModels()
    lateinit var navigation: ChartNavigation
    private val dataAdapter : ChartAdapter by lazy {
        ChartAdapter{ navigateToEditTransaction() }
    }

    override fun constructViewBinding(): FragmentChartBinding  =
        FragmentChartBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentChartBinding) {
        observeActions()

        viewBinding.chartRecyclerview
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }

    private fun navigateToEditTransaction() {
        viewModel.navigateToEditTransaction()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTransactionList()
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is ChartViewModel.Action.ShowDataChartTransactionItem -> {
                    dataAdapter.updateData(action.list)
                }
            }
        }
    }

    override fun setupNavigation() {
        navigation = ChartNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    companion object {
        const val EDIT_ITEM = "EDIT_ITEM"

    }
}