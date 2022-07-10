package com.twoonethree.turtlemint.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.twoonethree.turtlemint.databinding.SingleissueBinding
import com.twoonethree.turtlemint.iinterface.IssueClicklistener
import com.twoonethree.turtlemint.room.RoomIssueModel

class IssueAdapter: RecyclerView.Adapter<IssueAdapter.ViewHolder>() {

    var issuelist = listOf<RoomIssueModel>()
    var listener:IssueClicklistener? = null

    class ViewHolder(val binding: SingleissueBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleissueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val issue = issuelist.get(position)
        holder.binding.title.text = issue.title
        holder.binding.username.text = issue.username
        holder.binding.updatedtime.text = issue.updatedtime
        holder.binding.description.text = issue.description
        holder.binding.comments.text = issue.comment.toString()
        Glide.with(holder.itemView.context).load(issue.avatar).into(holder.binding.avatar)

        holder.binding.next.setOnClickListener {
            listener?.onClickComment(issue)
        }
    }

    override fun getItemCount(): Int {
        return issuelist.size
    }
}