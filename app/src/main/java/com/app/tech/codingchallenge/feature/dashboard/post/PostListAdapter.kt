package com.app.tech.codingchallenge.feature.dashboard.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.tech.codingchallenge.R
import com.app.tech.codingchallenge.core.data.db.entity.Post
import com.app.tech.codingchallenge.core.utils.AutoUpdatableAdapter
import com.app.tech.codingchallenge.databinding.ItemPostBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class PostListAdapter @Inject constructor() :
    RecyclerView.Adapter<PostListAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<Post> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    internal var clickListener: (Post) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_post
        )

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val post = collection[position]
            item = post
            executePendingBindings()
            holder.itemView.setOnClickListener {
                clickListener.invoke(post)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class Holder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding =
                    DataBindingUtil.inflate<ItemPostBinding>(
                        inflater,
                        layout,
                        parent,
                        false
                    )
                return Holder(
                    binding
                )
            }
        }
    }
}
