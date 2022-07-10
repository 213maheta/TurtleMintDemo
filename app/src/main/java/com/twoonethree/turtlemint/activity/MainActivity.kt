package com.twoonethree.turtlemint.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.twoonethree.turtlemint.adapter.IssueAdapter
import com.twoonethree.turtlemint.databinding.ActivityMainBinding
import com.twoonethree.turtlemint.iinterface.IssueClicklistener
import com.twoonethree.turtlemint.model.MyCommentModel
import com.twoonethree.turtlemint.room.RoomIssueModel
import com.twoonethree.turtlemint.sealed.ApiData
import com.twoonethree.turtlemint.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding? = null
    private var issueadapter:IssueAdapter? = null
    private val mainviewmodel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mainviewmodel.insertAllIssue()
        setRecyclerView()

        binding?.refresh?.setOnClickListener {
            mainviewmodel.insertAllIssue()
        }

        lifecycleScope.launchWhenCreated {
            mainviewmodel.repositoty.commentlistflow.collect {
                when(it)
                {
                    ApiData.loading -> {
                        binding?.progressbar?.visibility = View.VISIBLE
                    }
                    ApiData.success -> {
                        updateRecyclerview()
                        binding?.progressbar?.visibility = View.GONE
                    }
                    ApiData.failed -> {
                        withContext(Dispatchers.Main){
                            binding?.progressbar?.visibility = View.GONE
                            Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setRecyclerView() {
        issueadapter = IssueAdapter()
        issueadapter?.listener = object:IssueClicklistener{
            override fun onClickComment(issue: RoomIssueModel) {
               val data = Gson().toJson(issue)
               startActivity(Intent(this@MainActivity, CommentActivity::class.java).putExtra("issue", data))
            }
        }
        issueadapter?.issuelist = mainviewmodel.getAllIssue()
        binding?.rcvIssue?.apply {
            adapter = issueadapter
        }
        noDataVisibility()
    }

    suspend fun updateRecyclerview()
    {
        issueadapter?.issuelist = mainviewmodel.getAllIssue()
        withContext(Dispatchers.Main)
        {
            noDataVisibility()
            issueadapter?.notifyDataSetChanged()
        }
    }

    private fun noDataVisibility()
    {
        if(issueadapter?.issuelist?.size == 0)
        {
            binding?.nodata?.visibility = View.VISIBLE
        }
        else
        {
            binding?.nodata?.visibility = View.GONE
        }
    }
}