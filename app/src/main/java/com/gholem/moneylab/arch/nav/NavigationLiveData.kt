package com.gholem.moneylab.arch.nav

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.gholem.moneylab.arch.viewmodel.SingleObserver
import com.gholem.moneylab.arch.viewmodel.SingleState

class NavigationLiveData<T> : MutableLiveData<SingleState<T>>() {

    fun observe(owner: LifecycleOwner, block: (T) -> Unit) {
        super.observe(owner, SingleObserver(block))
    }

    fun emit(event: T) {
        postValue(SingleState(event))
    }
}