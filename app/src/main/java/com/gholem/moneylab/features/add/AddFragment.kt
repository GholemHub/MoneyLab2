package com.gholem.moneylab.features.add

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAddBinding
import com.gholem.moneylab.domain.model.AddFragmentDataModel
import com.gholem.moneylab.features.add.adapter.TransactionsAddAdapter
import com.gholem.moneylab.features.add.viewmodel.AddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel>() {

    private val viewModel: AddViewModel by viewModels()

    lateinit var adapter: TransactionsAddAdapter

    override fun constructViewBinding(): FragmentAddBinding =
        FragmentAddBinding.inflate(layoutInflater)

    private val dataAdapter: TransactionsAddAdapter by lazy {
        TransactionsAddAdapter()
    }


    override fun init(viewBinding: FragmentAddBinding) {
        viewModel.init()
        //TODO Dont know where have to set the adapter(ask where id viewBinding and why i dont see it)
        adapter = TransactionsAddAdapter()

        viewBinding.transactionsRecyclerView.adapter = adapter
        viewBinding.transactionsRecyclerView.layoutManager = LinearLayoutManager(context)

        //////////////////////
        dataAdapter.setData(getMockData())

        viewBinding.transactionsRecyclerView
            .apply {
                layoutManager = LinearLayoutManager(context)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }

    private fun getMockData(): List<AddFragmentDataModel> = listOf(
        AddFragmentDataModel.Category(1, "sdf"),
        AddFragmentDataModel.Transaction(10, "01.02.2000"),
        AddFragmentDataModel.Transaction(120, "01.12.2000"),
        AddFragmentDataModel.Transaction(42, "02.02.2000"),
        AddFragmentDataModel.NewTransaction("Add")
    )

    override fun setupNavigation() {

    }
}