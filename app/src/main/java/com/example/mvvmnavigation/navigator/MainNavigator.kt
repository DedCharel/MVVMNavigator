package com.example.mvvmnavigation.navigator

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmnavigation.Event
import com.example.mvvmnavigation.MainActivity
import com.example.mvvmnavigation.R
import com.example.mvvmnavigation.screens.base.BaseScreen

const val ARG_SCREEN = "SCREEN"

class MainNavigator(application: Application): AndroidViewModel(application), Navigator {

    val whenActivityActive  = MainActivityActions()

    private val _result = MutableLiveData<Event<Any>>()
    val result: LiveData<Event<Any>> = _result

    override fun launch(screen: BaseScreen) = whenActivityActive{
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive{
        if (result != null){
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(getApplication(), messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int): String {
        return getApplication<Application>().getString(messageRes)
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addTOBackStack: Boolean = true){
        val fragment =screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to  screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addTOBackStack) transaction.addToBackStack(null)
        transaction.replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}