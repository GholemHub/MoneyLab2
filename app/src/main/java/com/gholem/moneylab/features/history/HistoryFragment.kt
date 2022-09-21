package com.gholem.moneylab.features.history

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentHistoryBinding
import com.gholem.moneylab.features.history.adapter.HistoryAdapter
import com.gholem.moneylab.features.history.navigation.HistoryNavigation
import com.gholem.moneylab.features.history.navigation.HistoryNavigationEvent
import com.gholem.moneylab.features.history.viewmodel.HistoryViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryViewModel>() {

    private val viewModel: HistoryViewModel by viewModels()
    lateinit var navigation: HistoryNavigation

    private val dataAdapter: HistoryAdapter by lazy {
        HistoryAdapter { getPositionOfEditItem(it) }
    }

    override fun constructViewBinding(): FragmentHistoryBinding =
        FragmentHistoryBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentHistoryBinding) {
        observeActions()

        viewBinding.historyRecyclerview
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }

    override fun setupNavigation() {
        navigation = HistoryNavigation(navControllerWrapper)
        viewModel.navigation.observe(this) {
            it as HistoryNavigationEvent.ToEditTransaction
            navigation.navigate(it)
        }
    }

    override fun onResume() {
        super.onResume()
        observeActions()
        viewModel.fetchTransactionList()
    }

    private fun getPositionOfEditItem(_position: Long) {
        viewModel.navigateToEditTransaction(_position)
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is HistoryViewModel.Action.ShowDataHistoryTransactionItem -> {
                    dataAdapter.updateData(action.list)
                }
            }
        }
    }
}