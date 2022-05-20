package com.app.tech.codingchallenge.core.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.app.tech.codingchallenge.core.common.LoadingDialog
import com.app.tech.codingchallenge.core.extension.observe
import com.google.gson.Gson
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * To act as a super class for all other activities.
 * Passing ViewDataBinding to include initialization which is common for all activities
 * that's using databinding
 */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    protected val gson = Gson()

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected lateinit var binding: V

    //For having common loader pop-up for all screens
    private val loadingDialog: LoadingDialog by lazy(mode = LazyThreadSafetyMode.NONE) {
        LoadingDialog(this)
    }

    protected abstract fun getNavController(): NavController?

    protected abstract fun getViewModel(): BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)

        onCreated(savedInstanceState)

        initBaseObserver()
    }

    protected abstract fun onCreated(instance: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    //For simple yet dynamic control (basic control) of toolbar
    fun setToolbar(show: Boolean = false, showBackButton: Boolean = false, title: String = "") {
        val actionBar = supportActionBar

        actionBar?.run {
            if (show) {
                show()
                displayOptions = ActionBar.DISPLAY_SHOW_TITLE

                setHomeButtonEnabled(showBackButton)
                setDisplayHomeAsUpEnabled(showBackButton)

                if (title != "") {
                    setDisplayShowTitleEnabled(true)
                    this@run.title = title
                } else
                    setDisplayShowTitleEnabled(false)

            } else
                hide()
        }
    }

    fun showLoading(isLoading: Boolean) {
        loadingDialog.let {
            if (isLoading && !loadingDialog.isShowing)
                loadingDialog.show()
            else if (!isLoading && loadingDialog.isShowing) {
                loadingDialog.dismiss()
            }
        }
    }

    private fun initBaseObserver() {
        getViewModel()?.apply {
            observe(navigationId) {
                it?.let {
                    if (it.first != -1) {
                        getNavController()?.navigate(it.first, it.second)
                        //To clear the live data after the navigation execution
                        setNavigationId(-1 to null)
                    }
                }
            }

            (application as? App)?.internetConnectionStream
                ?.onEach {
                    setHasInternetConnection(it)
                }?.launchIn(lifecycleScope)
        }
    }
}