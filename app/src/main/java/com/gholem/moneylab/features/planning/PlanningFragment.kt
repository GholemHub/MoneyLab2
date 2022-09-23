package com.gholem.moneylab.features.planning

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentPlanningBinding
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.planning.adapter.PlanningAdapter
import com.gholem.moneylab.features.planning.navigation.PlanningNavigation
import com.gholem.moneylab.features.planning.viewmodel.PlanningViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanningFragment : BaseFragment<FragmentPlanningBinding, PlanningViewModel>() {

    lateinit var planningNavigation: PlanningNavigation
    private val viewModel: PlanningViewModel by viewModels()


    private val dataAdapter: PlanningAdapter by lazy {
        PlanningAdapter { saveTransaction(it) }
    }

    override fun constructViewBinding(): FragmentPlanningBinding =
        FragmentPlanningBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentPlanningBinding) {
        createChart(viewBinding)


        viewBinding.planningRecyclerview.setHasFixedSize(true)
        viewBinding.planningRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.planningRecyclerview.adapter = dataAdapter

        observeActions()
        initViewModel()
    }

    private fun createChart(viewBinding: FragmentPlanningBinding) {
        val model: AAChartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .title("title")
            .subtitle("subtitle")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Tokyo")
                        .data(
                            arrayOf(
                                7.0,
                                6.9,
                                9.5,
                                14.5,
                                18.2,
                                21.5,
                                25.2,
                            )
                        ),
                )
            )

        viewBinding.aaChartView.aa_drawChartWithChartModel(model)
        viewBinding.aaChartView.aa_refreshChartWithChartModel(model)
    }

    override fun setupNavigation() {
        planningNavigation = PlanningNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, planningNavigation::navigate)
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
                is PlanningViewModel.Action.ShowTransactions ->
                    dataAdapter.setListData(action.transactions)
            }
        }
    }
}

