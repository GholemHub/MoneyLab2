package com.gholem.moneylab.arch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.arch.nav.NavControllerWrapper
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>() : Fragment() {

    @Inject
    lateinit var navControllerWrapper: NavControllerWrapper

    private var viewBinding: ViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavWrapper()
        setupNavigation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = constructViewBinding()
        (viewBinding as? VB)?.let { init(it) }
        return viewBinding?.root
    }

    @Suppress("UNCHECKED_CAST")
    fun getViewBinding(): VB = viewBinding as VB

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun initNavWrapper() {
        navControllerWrapper.init(this)
    }

    abstract fun constructViewBinding(): VB
    abstract fun init(viewBinding: VB)
    abstract fun setupNavigation()
}