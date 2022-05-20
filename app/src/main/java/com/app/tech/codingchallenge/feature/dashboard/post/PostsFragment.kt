package com.app.tech.codingchallenge.feature.dashboard.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.app.tech.codingchallenge.R
import com.app.tech.codingchallenge.core.base.BaseActivity
import com.app.tech.codingchallenge.core.base.BaseFragment
import com.app.tech.codingchallenge.core.base.BaseViewModel
import com.app.tech.codingchallenge.core.extension.observe
import com.app.tech.codingchallenge.databinding.FragmentPostsBinding
import com.app.tech.codingchallenge.feature.dashboard.post_details.PostDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding>() {

    private val viewModel: PostsViewModel by viewModels()

    @Inject
    lateinit var adapter: PostListAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_posts

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getNavController(): NavController = findNavController()

    override fun onResume() {
        super.onResume()
        (activity as BaseActivity<*>).setToolbar(show = true, title = "Posts")
    }

    override fun onCreated(savedInstance: Bundle?) {
        initBinding()
        initViews()
        initObserver()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViews() {
        binding.postRvData.adapter = adapter
        binding.postRvData.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        binding.postSrlData.setOnRefreshListener {
            viewModel.fetchPosts()
            binding.postSrlData.isRefreshing = false
        }
        adapter.clickListener = {
            viewModel.setNavigationId(
                R.id.action_postsFragment_to_postDetailsFragment to
                        bundleOf(
                            PostDetailsFragment.ARGS_POST to gson.toJson(it)
                        )
            )
        }
    }

    private fun initObserver() {
        viewModel.apply {
            observe(posts) {
                it?.let { adapter.collection = it }
            }

            observe(searchTextQuery) {
                it?.let { searchQuery ->
                    adapter.collection = posts.value?.let {
                        it.filter { post ->
                            post.title.contains(searchQuery, ignoreCase = true) ||
                                    post.body.contains(searchQuery, ignoreCase = true)
                        }
                    } ?: emptyList()
                } ?: kotlin.run { adapter.collection = posts.value ?: emptyList() }
            }
        }
    }
}