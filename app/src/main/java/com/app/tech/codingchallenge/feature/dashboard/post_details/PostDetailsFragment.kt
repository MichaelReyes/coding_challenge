package com.app.tech.codingchallenge.feature.dashboard.post_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.app.tech.codingchallenge.R
import com.app.tech.codingchallenge.core.base.BaseActivity
import com.app.tech.codingchallenge.core.base.BaseFragment
import com.app.tech.codingchallenge.core.base.BaseViewModel
import com.app.tech.codingchallenge.core.extension.fromJson
import com.app.tech.codingchallenge.core.extension.observe
import com.app.tech.codingchallenge.databinding.FragmentPostDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>() {

    private val viewModel: PostDetailsViewModel by viewModels()

    @Inject
    lateinit var adapter: PostCommentListAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_post_details


    override fun getViewModel(): BaseViewModel = viewModel

    override fun getNavController(): NavController = findNavController()

    override fun onCreated(savedInstance: Bundle?) {
        initBinding()
        initViews()
        initObserver()
        checkArguments()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViews() {
        binding.postDetailsRvComments.adapter = adapter
        binding.postDetailsRvComments.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
    }

    private fun initObserver() {
        viewModel.apply {
            observe(postComments) {
                it?.let {
                    adapter.collection = it
                }
            }

            observe(post) {
                it?.let {
                    (activity as BaseActivity<*>).setToolbar(
                        show = true,
                        title = it.title,
                        showBackButton = true
                    )
                }
            }
        }
    }

    private fun checkArguments() {
        arguments?.let { args ->
            if (args.containsKey(ARGS_POST)) {
                viewModel.setPost(gson.fromJson(args.getString(ARGS_POST, "")))
            }
        }
    }

    companion object {
        const val ARGS_POST = "_args_post"
    }
}