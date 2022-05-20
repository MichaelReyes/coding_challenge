package com.app.tech.codingchallenge.feature.dashboard.post_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.tech.codingchallenge.R
import com.app.tech.codingchallenge.core.data.db.entity.Comment
import com.app.tech.codingchallenge.core.data.db.entity.Post
import com.app.tech.codingchallenge.core.utils.AutoUpdatableAdapter
import com.app.tech.codingchallenge.databinding.ItemCommentBinding
import com.app.tech.codingchallenge.databinding.ItemPostBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class PostCommentListAdapter @Inject constructor() :
    RecyclerView.Adapter<PostCommentListAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<Comment> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_comment
        )

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val comment = collection[position]
            item = comment
            executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class Holder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding =
                    DataBindingUtil.inflate<ItemCommentBinding>(
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
