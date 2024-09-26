package com.example.pinappchallenge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pinappchallenge.data.models.Comment
import com.example.pinappchallenge.data.models.Post
import com.example.pinappchallenge.databinding.ItemCommentBinding
import com.example.pinappchallenge.databinding.ItemPostBinding
import com.example.pinappchallenge.helpers.hide
import com.example.pinappchallenge.helpers.show

/**
 * @author Axel Sanchez
 */
class CommentsAdapter(
    private var mItems: List<Comment?>
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment?, position: Int) {

            with(binding) {
                item?.let { comment ->
                    comment.name?.let { name ->
                        tvName.text = name
                    } ?: kotlin.run { tvName.hide() }

                    comment.email?.let { email ->
                        tvEmail.text = email
                    } ?: kotlin.run { tvEmail.hide() }

                    comment.body?.let { body ->
                        tvBody.text = body
                    } ?: kotlin.run { tvBody.hide() }

                    if (position == itemCount - 1) {
                        vSeparator.hide()
                    } else {
                        vSeparator.show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerRowBinding: ItemCommentBinding =
            ItemCommentBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(mItems[position], position)


    override fun getItemCount() = mItems.size
}