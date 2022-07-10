package com.twoonethree.turtlemint.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.twoonethree.turtlemint.databinding.SinglecommentBinding
import com.twoonethree.turtlemint.model.MyCommentModel

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var commentlist = listOf<MyCommentModel>()

    class ViewHolder(val binding: SinglecommentBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SinglecommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myCommentModel = commentlist.get(position)
        holder.binding.username.text = myCommentModel.username
        holder.binding.updatedtime.text = myCommentModel.updatedtime
        holder.binding.comment.text = myCommentModel.comment
        holder.binding.like.text = myCommentModel.like.toString()
        holder.binding.dislike.text = myCommentModel.dislike.toString()
        Glide.with(holder.itemView.context).load(myCommentModel.avatar).circleCrop().into(holder.binding.avatar)
    }

    override fun getItemCount(): Int {
        return commentlist.size
    }
}