package com.example.pinappchallenge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pinappchallenge.data.models.Post
import com.example.pinappchallenge.databinding.ItemPostBinding
import com.example.pinappchallenge.helpers.hide
import com.example.pinappchallenge.helpers.show

/**
 * @author Axel Sanchez
 */
class PostAdapter(
    private var mItems: List<Post?>,
    private val itemClick: (Post?) -> Unit?
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post?, itemClick: (Post?) -> Unit?, position: Int) {

            with(binding) {
                item?.let { character ->
                    itemView.setOnClickListener {
                        itemClick(character)
                    }

                    character.title?.let { title ->
                        tvTitle.text = title
                    } ?: kotlin.run { tvTitle.hide() }

                    character.body?.let { body ->
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
        val recyclerRowBinding: ItemPostBinding =
            ItemPostBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(mItems[position], itemClick, position)


    override fun getItemCount() = mItems.size
}