package com.danielomeara.buildabudget.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.danielomeara.buildabudget.utils.interfaces.KeyboardListener

abstract class BaseFragment<T : ViewDataBinding, VM: ViewModel> : Fragment() {

    private lateinit var binding: T
    protected abstract val viewModel: VM
    private var listener: KeyboardListener? = null

    abstract fun getFragmentView(): Int

    abstract fun getLifecycleOwner(): LifecycleOwner

    fun getViewBinding() = binding
    fun getListener() = listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = activity as? KeyboardListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            getFragmentView(),
            container,
            false)

        binding.lifecycleOwner = getLifecycleOwner()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    abstract fun setUpListeners()

}