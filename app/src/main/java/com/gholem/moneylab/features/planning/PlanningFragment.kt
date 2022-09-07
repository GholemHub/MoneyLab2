package com.gholem.moneylab.features.planning

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentPlanningBinding
import com.gholem.moneylab.features.planning.adapter.PlanningAdapter
import com.gholem.moneylab.features.planning.adapter.item.PersonsItem
import com.gholem.moneylab.features.planning.navigation.PlanningNavigation
import com.gholem.moneylab.features.planning.viewmodel.PlanningViewModel
import com.gholem.moneylab.repository.network.api.TransactionApi
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import timber.log.Timber.d
import timber.log.Timber.i

@AndroidEntryPoint
class PlanningFragment : BaseFragment<FragmentPlanningBinding, PlanningViewModel>() {

    private var dataAdapter = PlanningAdapter()

    private val viewModel: PlanningViewModel by viewModels()

    lateinit var planningNavigation: PlanningNavigation

    override fun constructViewBinding(): FragmentPlanningBinding =
        FragmentPlanningBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentPlanningBinding) {
        viewBinding.planningRecyclerview.setHasFixedSize(true)
        viewBinding.planningRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.planningRecyclerview.adapter = dataAdapter

        observeActions()
        initViewModel()
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

    override fun setupNavigation() {
        planningNavigation = PlanningNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, planningNavigation::navigate)
    }
}

