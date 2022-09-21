package com.example.f1_app.presentation.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

//    val disposable = CompositeDisposable()

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return childFragmentInjector
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        disposables.clear()
    }
}