package com.example.mvvmnavigation.screens.base

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VIewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Not yet implemented")
    }


}

inline fun <reified VM: ViewModel> BaseFragment.screenViewModel() = viewModels<VM> {
    VIewModelFactory()
}