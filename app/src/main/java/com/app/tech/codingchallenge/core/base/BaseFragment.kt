package com.app.tech.codingchallenge.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.app.tech.codingchallenge.core.extension.observe
import com.google.gson.Gson

/**
 * To act as a super class for all other fragments.
 * Passing ViewDataBinding to include initialization which is common for all fragments
 * that's using databinding
 * Sample usage SampleFragment<FragmentSampleBinding>
 */
abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected val gson = Gson()

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected lateinit var baseView: View

    protected lateinit var binding: V

    protected abstract fun onCreated(savedInstance: Bundle?)

    private var previouslyInitialized = false

    //If need to work with common function, livedata need to observe, etc. among the viewModels
    protected abstract fun getViewModel(): BaseViewModel?

    protected abstract fun getNavController(): NavController?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        } else {
            previouslyInitialized = true
        }
        baseView = binding.root

        return baseView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!previouslyInitialized) {
            onCreated(savedInstanceState)
        }
        initBaseObserver()
    }

    protected fun showLoading(loading: Boolean) {
        activity?.let { a -> (a as BaseActivity<*>).showLoading(loading) }
    }

    private fun initBaseObserver() {
        getViewModel()?.apply {
            observe(loading) {
                it?.let {
                    showLoading(it)
                }
            }

            observe(navigationId) {
                it?.let {
                    if (it.first != -1) {
                        getNavController()?.navigate(it.first, it.second)
                        //To clear the live data after the navigation execution
                        setNavigationId(-1 to null)
                    }
                }
            }

            observe(error) {
                it?.let {
                    //show message for error
                }
            }
        }
    }

}