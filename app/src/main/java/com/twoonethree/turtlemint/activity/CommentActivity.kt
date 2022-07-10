package com.twoonethree.turtlemint.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.twoonethree.turtlemint.adapter.CommentAdapter
import com.twoonethree.turtlemint.databinding.ActivityCommentBinding
import com.twoonethree.turtlemint.model.MyCommentModel
import com.twoonethree.turtlemint.room.RoomIssueModel
import com.twoonethree.turtlemint.viewmodel.CommentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentActivity : AppCompatActivity() {

    private var binding: ActivityCommentBinding? = null
    private val commentviewmodel: CommentViewModel by viewModel()
    private var commentadapter:CommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val data = intent?.getStringExtra("issue")
        val issue = Gson().fromJson<RoomIssueModel>(data, RoomIssueModel::class.java)

        binding?.title?.text = issue.title
        binding?.username?.text = issue.username
        binding?.updatedtime?.text = issue.updatedtime
        binding?.description?.text = issue.description
        binding?.avatar?.let {
            Glide.with(this)
                .load(issue.avatar)
                .into(it) }

        setCommentRecyclerView(issue?.commentid)
        lifecycleScope.launchWhenCreated {
            CoroutineScope(Dispatchers.IO).launch {
                commentviewmodel.repositoty.commentlistflow.collect {
                    updateRecyclerview(it)
                }
            }
        }
    }
    private fun setCommentRecyclerView(commentUrl: Int?) {
        commentadapter = CommentAdapter()
        commentviewmodel.getAllComment(urlend = commentUrl)
        binding?.rcvComment?.apply {
            adapter = commentadapter
        }
    }

    suspend fun updateRecyclerview(mutableList: List<MyCommentModel>)
    {
        commentadapter?.commentlist = mutableList
        withContext(Dispatchers.Main)
        {
            noDataVisibility()
            commentadapter?.notifyDataSetChanged()
        }
    }

    private fun noDataVisibility()
    {
        if(commentadapter?.commentlist?.size == 0)
        {
            binding?.nodata?.visibility = View.VISIBLE
        }
        else
        {
            binding?.nodata?.visibility = View.GONE
        }
    }
}