package com.app.tech.codingchallenge.feature.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.app.tech.codingchallenge.R
import com.app.tech.codingchallenge.core.base.BaseActivity
import com.app.tech.codingchallenge.core.base.BaseViewModel
import com.app.tech.codingchallenge.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_dashboard

    override fun getNavController(): NavController? = null

    override fun getViewModel(): BaseViewModel? = null

    override fun onCreated(instance: Bundle?) {
        setSupportActionBar(binding.toolbar)
    }
}