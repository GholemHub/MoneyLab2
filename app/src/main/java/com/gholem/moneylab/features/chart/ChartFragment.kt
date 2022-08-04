package com.gholem.moneylab.features.chart

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentChartBinding
import com.gholem.moneylab.features.chart.adapter.ChartAdapter
import com.gholem.moneylab.features.chart.viewmodel.ChartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding,ChartViewModel>() {

    private val viewModel: ChartViewModel by viewModels()

    private val dataAdapter = ChartAdapter()

    override fun constructViewBinding(): FragmentChartBinding  =
        FragmentChartBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentChartBinding) {
        viewModel.reedDataFromRoom()
        viewModel.createRoomDate()

        viewBinding.chartRecyclerview
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }

    override fun setupNavigation() {

    }
}